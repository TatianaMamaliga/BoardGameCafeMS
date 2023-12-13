package com.boardcafe.ms.services.util;

import com.boardcafe.ms.models.dtos.EventReservationDTO;
import com.boardcafe.ms.models.dtos.enums.ReservationStatusDTO;
import com.boardcafe.ms.models.entities.EventReservation;
import com.boardcafe.ms.models.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class EventReservationConverter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private EventReservationConverter() {
    }

    public EventReservationDTO EntityToDTO(EventReservation reservation) {
        EventReservationDTO reservationDTO = new EventReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setDate(reservation.getDate());
        reservationDTO.setStartTime(reservation.getStartTime());
        reservationDTO.setEndTime(reservation.getEndTime());

        ReservationStatusDTO reservationStatusDTO = objectMapper.convertValue(reservation.getStatus(), ReservationStatusDTO.class);
        reservationDTO.setReservationStatus(reservationStatusDTO);

        Long eventId = reservation.getEvent().getId();
        reservationDTO.setEventId(eventId);

        User user = reservation.getUser();

        reservationDTO.setUserId(user.getId());

        reservationDTO.setCreatedAt(reservation.getCreatedAt());
        reservationDTO.setModifiedAt(reservation.getModifiedAt());
        return reservationDTO;
    }
}