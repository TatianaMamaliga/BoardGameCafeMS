package com.boardcafe.ms.services.util;

import com.boardcafe.ms.models.dtos.TableReservationDTO;
import com.boardcafe.ms.models.dtos.enums.ReservationStatusDTO;
import com.boardcafe.ms.models.entities.TableReservation;
import com.boardcafe.ms.models.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class TableReservationConverter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private TableReservationConverter() {
    }

    public TableReservationDTO EntityToDTO(TableReservation tableReservation) {
        TableReservationDTO tableReservationDTO = new TableReservationDTO();
        tableReservationDTO.setId(tableReservation.getId());
        tableReservationDTO.setDate(tableReservation.getDate());
        tableReservationDTO.setStartTime(tableReservation.getStartTime());
        tableReservationDTO.setEndTime(tableReservation.getEndTime());
        tableReservationDTO.setAttendees(tableReservation.getAttendees());
        tableReservationDTO.setGameTableId(tableReservation.getGameTable().getId());
        tableReservationDTO.setReservationStatus(objectMapper.convertValue(tableReservation.getStatus(), ReservationStatusDTO.class));
        tableReservationDTO.setCreatedAt(tableReservation.getCreatedAt());
        tableReservationDTO.setModifiedAt(tableReservation.getModifiedAt());
        User user = tableReservation.getUser();
        tableReservationDTO.setUserId(user.getId());
        return tableReservationDTO;
    }

    public static TableReservation DTOToEntity(TableReservationDTO tableReservationDTO) {
        TableReservation tableReservation = new TableReservation();
        tableReservation.setDate(tableReservationDTO.getDate());
        tableReservation.setStartTime(tableReservationDTO.getStartTime());
        tableReservation.setEndTime(tableReservationDTO.getEndTime());
        tableReservation.setAttendees(tableReservationDTO.getAttendees());
        return tableReservation;
    }
}