package com.boardcafe.ms.services;

import com.boardcafe.ms.models.dtos.ReservationDTO;
import com.boardcafe.ms.repositories.ReservationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ObjectMapper objectMapper;

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