package com.boardcafe.ms.services;

import com.boardcafe.ms.models.dtos.GameTableDTO;

public interface GameTableService {
    GameTableDTO createTable(GameTableDTO gameTableDTO);

    void deleteTableById(Long id);
}