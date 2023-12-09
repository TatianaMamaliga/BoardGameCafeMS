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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        reservationEntity.setCreatedAt(LocalDateTime.now());
        reservationEntity.setModifiedAt(LocalDateTime.now());

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
        reservationDTOResponse.setCreatedAt(LocalDateTime.now());
        reservationDTOResponse.setModifiedAt(LocalDateTime.now());
        return reservationDTOResponse;
    }

    @Override
    public ReservationDTO getReservationById(Long id) {
        Reservation reservationEntity = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + id));

        return convertToDTO(reservationEntity);
    }

    @Override
    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + id));
        reservation.setModifiedAt(LocalDateTime.now());
        if (reservation.getStatus().equals(ReservationStatus.CANCELED)) {
            throw new IllegalStateException("Reservation is already canceled");
        }
        reservation.setStatus(ReservationStatus.CANCELED);
        reservationRepository.save(reservation);
    }

    @Override
    public void changeReservationStatus(Long id, String status) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + id));
        reservation.setModifiedAt(LocalDateTime.now());

        switch (status) {
            case "confirm":
                if (reservation.getStatus().equals(ReservationStatus.CONFIRMED)) {
                    throw new IllegalStateException("Reservation is already confirmed");
                }
                reservation.setStatus(ReservationStatus.CONFIRMED);
                reservationRepository.save(reservation);
                break;
            case "complete":
                if (reservation.getStatus().equals(ReservationStatus.COMPLETED)) {
                    throw new IllegalStateException("Reservation is already confirmed");
                }
                reservation.setStatus(ReservationStatus.COMPLETED);
                reservationRepository.save(reservation);
                break;
            default:
                throw new IllegalArgumentException("Invalid status: " + status);
        }
    }


    @Override
    public List<ReservationDTO> getAllReservations() {
        List<Reservation> allReservations = reservationRepository.findAll();
        List<ReservationDTO> allReservationsDTO = new ArrayList<>();

        if (allReservations.isEmpty()) {
            throw new EntityNotFoundException("Oops. There are no reservations here.");
        }

        for (Reservation reservation : allReservations) {
            ReservationDTO reservationDTO = convertToDTO(reservation);
            allReservationsDTO.add(reservationDTO);
        }
        return allReservationsDTO;
    }

    @Override
    public void deleteReservationById(Long id) {
        Reservation reservationEntity = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + id));
        reservationRepository.delete(reservationEntity);
    }

    public ReservationDTO convertToDTO(Reservation reservation) {
        ReservationDTO reservationDTO = objectMapper.convertValue(reservation, ReservationDTO.class);
        ReservationStatus reservationStatus = reservation.getStatus();
        ReservationStatusDTO reservationStatusDTO = objectMapper.convertValue(reservationStatus, ReservationStatusDTO.class);

        Long eventId = reservation.getEvent().getId();
        Long gameTableId = reservation.getGameTable().getId();
        Long userId = reservation.getUser().getId();

        reservationDTO.setReservationStatus(reservationStatusDTO);
        reservationDTO.setEventId(eventId);
        reservationDTO.setGameTableId(gameTableId);
        reservationDTO.setUserId(userId);

        return reservationDTO;
    }
}