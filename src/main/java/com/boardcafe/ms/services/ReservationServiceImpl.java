package com.boardcafe.ms.services;

import com.boardcafe.ms.exceptions.EntityNotFoundException;
import com.boardcafe.ms.models.dtos.ReservationDTO;
import com.boardcafe.ms.models.dtos.ReservationStatusDTO;
import com.boardcafe.ms.models.entities.*;
import com.boardcafe.ms.repositories.EventRepository;
import com.boardcafe.ms.repositories.GameTableRepository;
import com.boardcafe.ms.repositories.ReservationRepository;
import com.boardcafe.ms.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final GameTableRepository gameTableRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        reservationDTO.setReservationStatus(ReservationStatusDTO.NEW);
        Reservation reservationEntity = objectMapper.convertValue(reservationDTO, Reservation.class);
        reservationEntity.setStatus(ReservationStatus.NEW);

        Event event = eventRepository.findById(reservationDTO.getEventId())
                .orElseThrow(() -> new EntityNotFoundException("Event not found with given id"));
        GameTable gameTable = gameTableRepository.findById(reservationDTO.getGameTableId())
                .orElseThrow(() -> new EntityNotFoundException("Game table not found with given id"));
        User user = userRepository.findById(reservationDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with given id"));

        reservationEntity.setEvent(event);
        reservationEntity.setGameTable(gameTable);
        reservationEntity.setUser(user);

        Reservation savedReservation = reservationRepository.save(reservationEntity);
        ReservationDTO reservationDTOResponse = objectMapper.convertValue(savedReservation, ReservationDTO.class);

        reservationDTOResponse.setReservationStatus(ReservationStatusDTO.NEW);
        reservationDTOResponse.setEventId(event.getId());
        reservationDTOResponse.setGameTableId(gameTable.getId());
        reservationDTOResponse.setUserId(user.getId());
        return reservationDTOResponse;
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