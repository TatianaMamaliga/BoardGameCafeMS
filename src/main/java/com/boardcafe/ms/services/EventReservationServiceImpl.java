package com.boardcafe.ms.services;

import com.boardcafe.ms.exceptions.EntityNotFoundException;
import com.boardcafe.ms.exceptions.EventCapacityNotAvailable;
import com.boardcafe.ms.exceptions.UserAlreadyRegisteredToEvent;
import com.boardcafe.ms.models.dtos.EventReservationDTO;
import com.boardcafe.ms.models.dtos.enums.ReservationStatusDTO;
import com.boardcafe.ms.models.entities.Event;
import com.boardcafe.ms.models.entities.EventReservation;
import com.boardcafe.ms.models.entities.Reservation;
import com.boardcafe.ms.models.entities.User;
import com.boardcafe.ms.models.entities.enums.ReservationStatus;
import com.boardcafe.ms.repositories.EventRepository;
import com.boardcafe.ms.repositories.EventReservationRepository;
import com.boardcafe.ms.repositories.UserRepository;
import com.boardcafe.ms.services.util.EventReservationConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventReservationServiceImpl implements EventReservationService {

    private final EventReservationRepository eventReservationRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final EventReservationConverter eventReservationConverter;


    @Override
    public EventReservationDTO createReservation(EventReservationDTO eventReservationDTO, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with given id: " + eventId));
        if (!hasCapacity(event)) {
            throw new EventCapacityNotAvailable("There are no available spots for this event.");
        }

        if (isUserAlreadyRegistered(eventId, eventReservationDTO.getUserId())) {
            throw new UserAlreadyRegisteredToEvent("User is already registered to the event");
        }

        event.setCapacity(event.getCapacity() - 1);
        eventRepository.save(event);

        User user = userRepository.findById(eventReservationDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with given id: " + eventId));

        EventReservation eventReservation = objectMapper.convertValue(eventReservationDTO, EventReservation.class);

        eventReservation.setEvent(event);
        eventReservation.setCreatedAt(LocalDateTime.now());
        eventReservation.setModifiedAt(LocalDateTime.now());
        eventReservation.setStatus(ReservationStatus.NEW);
        eventReservation.setDate(event.getDate());
        eventReservation.setStartTime(event.getStartTime());
        eventReservation.setEndTime(event.getEndTime());
        eventReservation.setUser(user);

        EventReservation savedEventReservation = eventReservationRepository.save(eventReservation);

        ReservationStatusDTO reservationStatusDTO = objectMapper.convertValue(savedEventReservation.getStatus(), ReservationStatusDTO.class);

        EventReservationDTO eventReservationDTOResponse = objectMapper.convertValue(savedEventReservation, EventReservationDTO.class);
        eventReservationDTOResponse.setReservationStatus(reservationStatusDTO);
        eventReservationDTOResponse.setUserId(user.getId());
        eventReservationDTOResponse.setEventId(eventId);
        return eventReservationDTOResponse;
    }

    @Override
    public EventReservationDTO getReservationById(Long id) {
        EventReservation eventReservation = eventReservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event reservation not found with given id: " + id));
        return eventReservationConverter.EntityToDTO(eventReservation);
    }

    @Override
    public void cancelReservation(Long id) {
        EventReservation eventReservation = eventReservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event reservation not found with given id: " + id));
        eventReservation.setStatus(ReservationStatus.CANCELED);
        eventReservation.setModifiedAt(LocalDateTime.now());
        eventReservationRepository.save(eventReservation);
    }

    @Override
    public void changeReservationStatus(Long id, String status) {
        EventReservation eventReservation = eventReservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + id));
        eventReservation.setModifiedAt(LocalDateTime.now());

        switch (status) {
            case "confirm":
                if (eventReservation.getStatus().equals(ReservationStatus.CONFIRMED)) {
                    throw new IllegalStateException("Reservation is already confirmed");
                }
                eventReservation.setStatus(ReservationStatus.CONFIRMED);
                eventReservationRepository.save(eventReservation);
                break;
            case "complete":
                if (eventReservation.getStatus().equals(ReservationStatus.COMPLETED)) {
                    throw new IllegalStateException("Reservation is already completed");
                }
                eventReservation.setStatus(ReservationStatus.COMPLETED);
                eventReservationRepository.save(eventReservation);
                break;
            default:
                throw new IllegalArgumentException("Invalid status: " + status);
        }
    }

    @Override
    public List<EventReservationDTO> getAllReservations() {
        List<EventReservation> allEventReservations = eventReservationRepository.findAll();
        if (allEventReservations.isEmpty()) {
            throw new EntityNotFoundException("Oops. There are no reservations yet.");
        }
        return allEventReservations.stream()
                .map(eventReservationConverter::EntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReservationById(Long id) {
        EventReservation eventReservation = eventReservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + id));
        eventReservationRepository.delete(eventReservation);
    }

    private boolean hasCapacity(Event event) {
        return event.getCapacity() > 0;
    }

    private boolean isUserAlreadyRegistered(long userId, long eventId) {
        List<EventReservation> eventReservations = eventReservationRepository.findAll()
                .stream()
                .filter(eventReservation -> eventReservation.getEvent().getId().equals(eventId))
                .collect(Collectors.toList());
        return eventReservations.stream()
                .anyMatch(eventReservation -> eventReservation.getUser().getId().equals(userId));
    }
}