package com.boardcafe.ms.services;

import com.boardcafe.ms.models.dtos.TableReservationDTO;

import java.util.List;

public interface TableReservationService {
    TableReservationDTO createTableReservation(TableReservationDTO tableReservationDTO, Long tableId);

    TableReservationDTO getTableReservationById(Long id);

    List<TableReservationDTO> getAllTableReservations();
}