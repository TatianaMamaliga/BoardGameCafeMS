package com.boardcafe.ms.services;

import com.boardcafe.ms.models.dtos.GameTableDTO;
import com.boardcafe.ms.models.dtos.ReservationDTO;

import java.util.List;

public interface GameTableService {
    GameTableDTO createTable(GameTableDTO gameTableDTO);

    GameTableDTO getTableById(Long id);

    List<GameTableDTO> getAllGameTables();

    List<ReservationDTO> getReservationsByTableId(Long tableId);

    void deleteTableById(Long id);
}