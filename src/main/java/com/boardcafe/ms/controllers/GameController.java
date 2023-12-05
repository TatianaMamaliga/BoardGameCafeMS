package com.boardcafe.ms.controllers;

import com.boardcafe.ms.services.GameService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }
}