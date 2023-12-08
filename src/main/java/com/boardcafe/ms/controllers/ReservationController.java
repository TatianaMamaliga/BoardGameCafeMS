package com.boardcafe.ms.controllers;

import com.boardcafe.ms.models.dtos.ReservationDTO;
import com.boardcafe.ms.services.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
        return ResponseEntity.ok(reservationService.createReservation(reservationDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable long id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> cancelReservation(@PathVariable long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.ok("Successfully cancelled reservation with id: " + id);
    }

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<String> changeReservationStatus(@PathVariable long id, @PathVariable String status) {
        reservationService.changeReservationStatus(id, status);
        return ResponseEntity.ok("Successfully " + status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable long id) {
        reservationService.deleteReservationById(id);
        return ResponseEntity.ok("Successfully deleted reservation with id: " + id);
    }
}