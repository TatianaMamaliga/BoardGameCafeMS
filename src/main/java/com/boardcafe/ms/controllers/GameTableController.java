package com.boardcafe.ms.controllers;

import com.boardcafe.ms.models.dtos.GameTableDTO;
import com.boardcafe.ms.services.GameTableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game-tables")
public class GameTableController {

    private final GameTableService gameTableService;

    public GameTableController(GameTableService gameTableService) {
        this.gameTableService = gameTableService;
    }

    @PostMapping
    public ResponseEntity<GameTableDTO> createTable(@RequestBody GameTableDTO gameTableDTO) {
        return ResponseEntity.ok(gameTableService.createTable(gameTableDTO));
    }

    @GetMapping
    public ResponseEntity<List<GameTableDTO>> getAllTables() {
        return ResponseEntity.ok(gameTableService.getAllGameTables());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameTableDTO> getTableById(@PathVariable long id) {
        return ResponseEntity.ok(gameTableService.getTableById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTableById(@PathVariable long id) {
        gameTableService.deleteTableById(id);
        return ResponseEntity.ok("Successfully deleted game table with id " + id);
    }
}