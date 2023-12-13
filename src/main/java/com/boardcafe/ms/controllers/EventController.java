package com.boardcafe.ms.controllers;

import com.boardcafe.ms.models.dtos.EventDTO;
import com.boardcafe.ms.services.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody @Valid EventDTO eventDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createEvent(eventDTO));
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<EventDTO> allEvents = eventService.getAllEvents();
        if (allEvents.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(allEvents);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable long id) {
        EventDTO eventDTO = eventService.getEventById(id);
        if (eventDTO == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(eventDTO);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEventById(@PathVariable long id) {
        eventService.deleteEventById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted event with id: " + id);
    }
}