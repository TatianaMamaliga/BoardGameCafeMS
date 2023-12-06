package com.boardcafe.ms.services;

import com.boardcafe.ms.models.dtos.ReservationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        return null;
    }

    @Override
    public void cancelReservation(Long id) {

    }

    @Override
    public List<ReservationDTO> viewAllReservations() {
        return null;
    }

    @Override
    public void deleteReservationById(Long id) {

    }
}