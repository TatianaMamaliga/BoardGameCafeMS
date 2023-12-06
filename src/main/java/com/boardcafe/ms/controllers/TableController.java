package com.boardcafe.ms.controllers;

import com.boardcafe.ms.services.GameTableService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TableController {

    private final GameTableService gameTableService;

    public TableController(GameTableService gameTableService) {
        this.gameTableService = gameTableService;
    }
}