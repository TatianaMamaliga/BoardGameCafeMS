package com.boardcafe.ms.controllers;

import com.boardcafe.ms.models.dtos.GameDTO;
import com.boardcafe.ms.services.GameService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.createGame(gameDTO));
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

    @GetMapping("/filter")
    public ResponseEntity<List<GameDTO>> findGamesByCategoryAndMaxPlayerAndPrice(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer maxPlayers,
            @RequestParam(required = false) Double price) {
        return ResponseEntity.ok(gameService.findGamesByCategoryAndMaxPlayersAndPrice(category, maxPlayers, price));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> getGameById(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.getGameById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGameById(@PathVariable long id) {
        gameService.deleteGameById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted game with id: " + id);
    }
}