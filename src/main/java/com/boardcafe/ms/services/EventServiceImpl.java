package com.boardcafe.ms.services;

import com.boardcafe.ms.exceptions.EntityNotFoundException;
import com.boardcafe.ms.models.dtos.EventDTO;
import com.boardcafe.ms.models.dtos.EventReservationDTO;
import com.boardcafe.ms.models.entities.Event;
import com.boardcafe.ms.models.entities.EventReservation;
import com.boardcafe.ms.repositories.EventRepository;
import com.boardcafe.ms.repositories.ReservationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final ReservationRepository reservationRepository;
    private final ObjectMapper objectMapper;
    //    private final ReservationService reservationService;
    private final ReservationServiceImpl reservationService;

    @Override
    public EventDTO createEvent(EventDTO eventDTO) {
        Event eventEntity = objectMapper.convertValue(eventDTO, Event.class);
        Event savedEventEntity = eventRepository.save(eventEntity);
        EventDTO eventDTOResponse = objectMapper.convertValue(savedEventEntity, EventDTO.class);
        return eventDTOResponse;
    }

    @Override
    public EventDTO getEventById(Long id) {
        Event eventEntity = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with the given ID: " + id));

        Set<EventReservationDTO> eventReservationDTOSet = getReservationDTOs(eventEntity.getEventReservations());

        EventDTO eventDTO = objectMapper.convertValue(eventEntity, EventDTO.class);
        eventDTO.setReservations(eventReservationDTOSet);
        return eventDTO;
    }

    @Override
    public List<EventDTO> getAllEvents() {
        List<EventDTO> allEventsDTO = new ArrayList<>();
        List<Event> allEvents = eventRepository.findAll();
        for (Event event : allEvents) {
            Set<EventReservationDTO> eventReservationDTOSet = getReservationDTOs(event.getEventReservations());
            EventDTO eventDTO = objectMapper.convertValue(event, EventDTO.class);
            eventDTO.setReservations(eventReservationDTOSet);
            allEventsDTO.add(eventDTO);
        }
        return allEventsDTO;
    }

    @Override
    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        return null;
    }

    @Override
    public void deleteEventById(Long id) {
        try {
            Event event = eventRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id));
            eventRepository.delete(event);
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }

    private Set<EventReservationDTO> getReservationDTOs(Set<EventReservation> eventReservations) {
        Set<EventReservationDTO> eventReservationDTOSet = new HashSet<>();
        for (EventReservation eventReservation : eventReservations) {
            EventReservationDTO eventReservationDTO = reservationService.convertToDTO(eventReservation);
            eventReservationDTOSet.add(eventReservationDTO);
        }
        return eventReservationDTOSet;
    }


}