package com.boardcafe.ms.services.util;

import com.boardcafe.ms.models.dtos.TableReservationDTO;
import com.boardcafe.ms.models.dtos.enums.ReservationStatusDTO;
import com.boardcafe.ms.models.entities.TableReservation;
import com.boardcafe.ms.models.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class TableReservationConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private TableReservationConverter() {
    }

    public static TableReservationDTO EntityToDTO(TableReservation reservation) {
        TableReservationDTO reservationDTO = new TableReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setDate(reservation.getDate());
        reservationDTO.setStartTime(reservation.getStartTime());
        reservationDTO.setEndTime(reservation.getEndTime());
        reservationDTO.setAttendees(reservation.getAttendees());
        reservationDTO.setGameTableId(reservation.getGameTable().getId());
        reservationDTO.setReservationStatus(objectMapper.convertValue(reservation.getStatus(), ReservationStatusDTO.class));
        reservationDTO.setCreatedAt(reservation.getCreatedAt());
        reservationDTO.setModifiedAt(reservation.getModifiedAt());
        User user = reservation.getUser();
        reservationDTO.setUserId(user.getId());
        return reservationDTO;
    }
}