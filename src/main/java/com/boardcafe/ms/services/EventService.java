package com.boardcafe.ms.services;

import com.boardcafe.ms.models.dtos.EventDTO;

import java.util.List;

public interface EventService {

    EventDTO createEvent(EventDTO eventDTO);

    EventDTO getEventById(Long id);

    List<EventDTO> getAllEvents();

    EventDTO updateEvent(Long id, EventDTO eventDTO);

    void deleteEventById(Long id);
}