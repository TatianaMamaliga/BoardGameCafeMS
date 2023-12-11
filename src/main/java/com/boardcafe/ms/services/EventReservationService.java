package com.boardcafe.ms.services;

import com.boardcafe.ms.models.dtos.EventReservationDTO;

import java.util.List;

public interface EventReservationService {
    EventReservationDTO createReservation(EventReservationDTO eventReservationDTO, Long eventId);

    EventReservationDTO getReservationById(Long id);

    void cancelReservation(Long id);

    void changeReservationStatus(Long id, String status);

    List<EventReservationDTO> getAllReservations();

    void deleteReservationById(Long id);
}