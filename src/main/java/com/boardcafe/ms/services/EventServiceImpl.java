package com.boardcafe.ms.services;

import com.boardcafe.ms.exceptions.EntityNotFoundException;
import com.boardcafe.ms.models.dtos.EventDTO;
import com.boardcafe.ms.models.entities.Event;
import com.boardcafe.ms.repositories.EventRepository;
import com.boardcafe.ms.repositories.ReservationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final ReservationRepository reservationRepository;
    private final ObjectMapper objectMapper;

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
        EventDTO eventDTO = objectMapper.convertValue(eventEntity, EventDTO.class);
        return eventDTO;
    }

    @Override
    public List<EventDTO> getAllEvents() {
        List<EventDTO> allEventsDTO = new ArrayList<>();
        List<Event> allEvents = eventRepository.findAll();
        for (Event event : allEvents) {
            EventDTO eventDTO = objectMapper.convertValue(event, EventDTO.class);
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
}