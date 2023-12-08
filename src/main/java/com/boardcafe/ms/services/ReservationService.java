package com.boardcafe.ms.services;

import com.boardcafe.ms.models.dtos.ReservationDTO;
import com.boardcafe.ms.models.dtos.ReservationStatusDTO;

import java.util.List;

public interface ReservationService {
    ReservationDTO createReservation(ReservationDTO reservationDTO);

    void cancelReservation(Long id);

    List<ReservationDTO> viewAllReservations();

    void deleteReservationById(Long id);
}