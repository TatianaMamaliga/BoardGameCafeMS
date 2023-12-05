package com.boardcafe.ms.controllers;

import com.boardcafe.ms.services.TableService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TableController {

    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }
}