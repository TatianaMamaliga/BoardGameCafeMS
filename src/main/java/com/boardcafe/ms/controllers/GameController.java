package com.boardcafe.ms.controllers;

import com.boardcafe.ms.models.dtos.GameDTO;
import com.boardcafe.ms.services.GameService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<GameDTO> createGame(@RequestBody @Valid GameDTO gameDTO) {
        return ResponseEntity.ok(gameService.createGame(gameDTO));
    }

    @GetMapping
    public ResponseEntity<List<GameDTO>> getAllGames() {
        List<GameDTO> allGames = gameService.getAllGames();
        if (allGames.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(allGames);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> getGameById(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.getGameById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGameById(@PathVariable long id) {
        gameService.deleteGameById(id);
        return ResponseEntity.ok("Successfully deleted game with id: " + id);
    }
}