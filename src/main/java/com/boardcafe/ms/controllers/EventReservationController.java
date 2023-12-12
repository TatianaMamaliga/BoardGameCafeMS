package com.boardcafe.ms.controllers;

import com.boardcafe.ms.models.dtos.EventReservationDTO;
import com.boardcafe.ms.services.EventReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/events/")
public class EventReservationController {

    private final EventReservationService eventReservationService;

    public EventReservationController(EventReservationService eventReservationService) {
        this.eventReservationService = eventReservationService;
    }

    @PostMapping("/{id}/reservations")
    public ResponseEntity<EventReservationDTO> createReservation(@RequestBody EventReservationDTO eventReservationDTO, @PathVariable long id) {
        return ResponseEntity.ok(eventReservationService.createReservation(eventReservationDTO, id));
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<EventReservationDTO> getReservationById(@PathVariable long id) {
        return ResponseEntity.ok(eventReservationService.getReservationById(id));
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<EventReservationDTO>> getAllReservations() {
        return ResponseEntity.ok(eventReservationService.getAllReservations());
    }

    @PatchMapping("/reservations/{id}")
    public ResponseEntity<String> cancelReservation(@PathVariable long id) {
        eventReservationService.cancelReservation(id);
        return ResponseEntity.ok("Successfully cancelled reservation with id: " + id);
    }

    @PatchMapping("/reservations/{id}/{status}")
    public ResponseEntity<String> changeReservationStatus(@PathVariable long id, @PathVariable String status) {
        eventReservationService.changeReservationStatus(id, status);
        String response = null;

        if (Objects.equals(status, "confirm")) {
            response = "confirmed";
        } else if (Objects.equals(status, "complete")) {
            response = "completed";
        }
        return ResponseEntity.ok("Event reservation successfully " + response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable long id) {
        eventReservationService.deleteReservationById(id);
        return ResponseEntity.ok("Successfully deleted reservation with id: " + id);
    }
}