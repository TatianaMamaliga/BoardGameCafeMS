package com.boardcafe.ms.services;

import com.boardcafe.ms.models.dtos.ReservationDTO;

import java.util.List;

public interface ReservationService {
    ReservationDTO createReservation(ReservationDTO reservationDTO);

    ReservationDTO getReservationById(Long id);

    void cancelReservation(Long id);

    void changeReservationStatus(Long id, String status);

    List<ReservationDTO> getAllReservations();

    void deleteReservationById(Long id);
}