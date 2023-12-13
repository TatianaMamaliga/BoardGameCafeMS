package com.boardcafe.ms.controllers;

import com.boardcafe.ms.models.dtos.TableReservationDTO;
import com.boardcafe.ms.services.TableReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/game-tables")
public class TableReservationController {

    private final TableReservationService tableReservationService;

    public TableReservationController(TableReservationService tableReservationService) {
        this.tableReservationService = tableReservationService;
    }

    @PostMapping("/{id}/reservations")
    public ResponseEntity<TableReservationDTO> createTableReservation(@PathVariable long id, @RequestBody TableReservationDTO tableReservationDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tableReservationService.createTableReservation(tableReservationDTO, id));
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<TableReservationDTO> getTableReservationById(@PathVariable long id) {
        return ResponseEntity.ok(tableReservationService.getTableReservationById(id));
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<TableReservationDTO>> getAllTableReservations() {
        return ResponseEntity.ok(tableReservationService.getAllTableReservations());
    }
}