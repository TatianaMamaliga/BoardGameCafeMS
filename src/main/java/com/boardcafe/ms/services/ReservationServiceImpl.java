package com.boardcafe.ms.services;

import com.boardcafe.ms.exceptions.EntityNotFoundException;
import com.boardcafe.ms.models.dtos.EventDTO;
import com.boardcafe.ms.models.dtos.EventReservationDTO;
import com.boardcafe.ms.models.dtos.enums.ReservationStatusDTO;
import com.boardcafe.ms.models.dtos.enums.ReservationTypeDTO;
import com.boardcafe.ms.models.entities.*;
import com.boardcafe.ms.models.entities.enums.ReservationStatus;
import com.boardcafe.ms.repositories.EventRepository;
import com.boardcafe.ms.repositories.GameTableRepository;
import com.boardcafe.ms.repositories.ReservationRepository;
import com.boardcafe.ms.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final GameTableRepository gameTableRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public EventReservationDTO createReservation(EventReservationDTO eventReservationDTO) {
        eventReservationDTO.setReservationStatus(ReservationStatusDTO.NEW);
        eventReservationDTO.setReservationType(ReservationTypeDTO.EVENT);

        EventReservation eventReservationEntity = objectMapper.convertValue(eventReservationDTO, EventReservation.class);
        eventReservationEntity.setStatus(ReservationStatus.NEW);
        eventReservationEntity.setCreatedAt(LocalDateTime.now());
        eventReservationEntity.setModifiedAt(LocalDateTime.now());

        Event event = eventRepository.findById(eventReservationDTO.getEventId())
                .orElseThrow(() -> new EntityNotFoundException("Event not found with given id"));
        GameTable gameTable = gameTableRepository.findById(eventReservationDTO.getGameTableId())
                .orElseThrow(() -> new EntityNotFoundException("Game table not found with given id"));
        User user = userRepository.findById(eventReservationDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with given id"));

        eventReservationEntity.setEvent(event);
        eventReservationEntity.setGameTable(gameTable);
        eventReservationEntity.setUser(user);

        EventReservation savedEventReservation = reservationRepository.save(eventReservationEntity);
        EventReservationDTO eventReservationDTOResponse = objectMapper.convertValue(savedEventReservation, EventReservationDTO.class);

        eventReservationDTOResponse.setReservationStatus(ReservationStatusDTO.NEW);
        eventReservationDTO.setReservationType(ReservationTypeDTO.EVENT);
        eventReservationDTOResponse.setEventId(event.getId());
        eventReservationDTOResponse.setGameTableId(gameTable.getId());
        eventReservationDTOResponse.setUserId(user.getId());
        eventReservationDTOResponse.setCreatedAt(LocalDateTime.now());
        eventReservationDTOResponse.setModifiedAt(LocalDateTime.now());
        return eventReservationDTOResponse;
    }

    @Override
    public EventReservationDTO createReservationSpecialEvent(EventReservationDTO eventReservationDTO, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with given id: " + eventId));
        EventDTO eventDTO = objectMapper.convertValue(event, EventDTO.class);

        eventReservationDTO.setReservationStatus(ReservationStatusDTO.NEW);
        eventReservationDTO.setDate(eventDTO.getDate());
        eventReservationDTO.setStartTime(eventDTO.getStartTime());
        eventReservationDTO.setEndTime(eventDTO.getEndTime());
        eventReservationDTO.setEventId(eventId);

        eventReservationDTO.setCreatedAt(LocalDateTime.now());
        eventReservationDTO.setModifiedAt(LocalDateTime.now());

        Long userId = eventReservationDTO.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with given id: " + userId));
        GameTable gameTable = gameTableRepository.findById(eventReservationDTO.getGameTableId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with given id: " + userId));
        EventReservation eventReservationEntity = objectMapper.convertValue(eventReservationDTO, EventReservation.class);
        eventReservationEntity.setEvent(event);
        eventReservationEntity.setUser(user);
        eventReservationEntity.setGameTable(gameTable);
        EventReservationDTO eventReservationDTOResponse = convertToDTO(eventReservationEntity);

        return eventReservationDTOResponse;
    }


    @Override
    public EventReservationDTO getReservationById(Long id) {
        EventReservation eventReservationEntity = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + id));

        return convertToDTO(eventReservationEntity);
    }

    @Override
    public void cancelReservation(Long id) {
        EventReservation eventReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + id));
        eventReservation.setModifiedAt(LocalDateTime.now());
        if (eventReservation.getStatus().equals(ReservationStatus.CANCELED)) {
            throw new IllegalStateException("Reservation is already canceled");
        }
        eventReservation.setStatus(ReservationStatus.CANCELED);
        reservationRepository.save(eventReservation);
    }

    @Override
    public void changeReservationStatus(Long id, String status) {
        EventReservation eventReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + id));
        eventReservation.setModifiedAt(LocalDateTime.now());

        switch (status) {
            case "confirm":
                if (eventReservation.getStatus().equals(ReservationStatus.CONFIRMED)) {
                    throw new IllegalStateException("Reservation is already confirmed");
                }
                eventReservation.setStatus(ReservationStatus.CONFIRMED);
                reservationRepository.save(eventReservation);
                break;
            case "complete":
                if (eventReservation.getStatus().equals(ReservationStatus.COMPLETED)) {
                    throw new IllegalStateException("Reservation is already completed");
                }
                eventReservation.setStatus(ReservationStatus.COMPLETED);
                reservationRepository.save(eventReservation);
                break;
            default:
                throw new IllegalArgumentException("Invalid status: " + status);
        }
    }


    @Override
    public List<EventReservationDTO> getAllReservations() {
        List<EventReservation> allEventReservations = reservationRepository.findAll();
        List<EventReservationDTO> allReservationsDTO = new ArrayList<>();

        if (allEventReservations.isEmpty()) {
            throw new EntityNotFoundException("Oops. There are no reservations here.");
        }

        for (EventReservation eventReservation : allEventReservations) {
            EventReservationDTO eventReservationDTO = convertToDTO(eventReservation);
            allReservationsDTO.add(eventReservationDTO);
        }
        return allReservationsDTO;
    }

    @Override
    public void deleteReservationById(Long id) {
        EventReservation eventReservationEntity = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + id));
        reservationRepository.delete(eventReservationEntity);
    }

    public EventReservationDTO convertToDTO(EventReservation eventReservation) {
        EventReservationDTO eventReservationDTO = objectMapper.convertValue(eventReservation, EventReservationDTO.class);
        ReservationStatus reservationStatus = eventReservation.getStatus();
        ReservationStatusDTO reservationStatusDTO = objectMapper.convertValue(reservationStatus, ReservationStatusDTO.class);

        Long eventId = eventReservation.getEvent().getId();
        Long gameTableId = eventReservation.getGameTable().getId();
        Long userId = eventReservation.getUser().getId();

        eventReservationDTO.setReservationStatus(reservationStatusDTO);
        eventReservationDTO.setEventId(eventId);
        eventReservationDTO.setGameTableId(gameTableId);
        eventReservationDTO.setUserId(userId);

        return eventReservationDTO;
    }
}