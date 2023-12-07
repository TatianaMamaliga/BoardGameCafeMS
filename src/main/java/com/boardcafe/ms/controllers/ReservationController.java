package com.boardcafe.ms.controllers;

import com.boardcafe.ms.models.dtos.ReservationDTO;
import com.boardcafe.ms.models.dtos.ReservationStatusDTO;
import com.boardcafe.ms.services.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/{status}")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO, @PathVariable ReservationStatusDTO status) {
        return ResponseEntity.ok(reservationService.createReservation(reservationDTO, status));
    }

}