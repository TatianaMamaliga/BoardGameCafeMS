package com.boardcafe.ms.services;

import com.boardcafe.ms.exceptions.EntityNotFoundException;
import com.boardcafe.ms.exceptions.StartTimeAndEndTimeAreInvalidException;
import com.boardcafe.ms.exceptions.TableNotAvailableException;
import com.boardcafe.ms.models.dtos.TableReservationDTO;
import com.boardcafe.ms.models.dtos.enums.ReservationStatusDTO;
import com.boardcafe.ms.models.entities.*;
import com.boardcafe.ms.models.entities.enums.GameTableStatus;
import com.boardcafe.ms.models.entities.enums.ReservationStatus;
import com.boardcafe.ms.repositories.GameTableRepository;
import com.boardcafe.ms.repositories.TableReservationRepository;
import com.boardcafe.ms.repositories.UserRepository;
import com.boardcafe.ms.services.util.TableReservationConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TableReservationServiceImpl implements TableReservationService {
    private final GameTableRepository gameTableRepository;
    private final TableReservationRepository tableReservationRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final TableReservationConverter tableReservationConverter;


    @Override
    public TableReservationDTO createTableReservation(TableReservationDTO tableReservationDTO, Long tableId) {
        if (!areStartTimeAndEndTimeValid(tableReservationDTO.getStartTime(), tableReservationDTO.getEndTime())) {
            throw new StartTimeAndEndTimeAreInvalidException("Start time must be before end time.");
        }

        GameTable table = gameTableRepository.findById(tableId)
                .orElseThrow(() -> new EntityNotFoundException("Table not found with id: " + tableId));
        Long userId = tableReservationDTO.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        if (!isTableAvailable(tableReservationDTO, table)) {
            throw new TableNotAvailableException("Selected interval isn't available, please redefine interval");
        }

        TableReservation tableReservation = objectMapper.convertValue(tableReservationDTO, TableReservation.class);
        tableReservation.setGameTable(table);
        tableReservation.setCreatedAt(LocalDateTime.now());
        tableReservation.setModifiedAt(LocalDateTime.now());
        tableReservation.setStatus(ReservationStatus.NEW);
        tableReservation.setUser(user);

        TableReservation savedTableReservation = tableReservationRepository.save(tableReservation);

        TableReservationDTO tableReservationDTOResponse = objectMapper.convertValue(savedTableReservation, TableReservationDTO.class);
        tableReservationDTOResponse.setReservationStatus(ReservationStatusDTO.NEW);
        tableReservationDTOResponse.setGameTableId(tableId);
        tableReservationDTOResponse.setUserId(user.getId());

        return tableReservationDTOResponse;
    }

    @Override
    public TableReservationDTO getTableReservationById(Long id) {
        TableReservation tableReservation = tableReservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Table reservation not found with id: " + id));
        return tableReservationConverter.EntityToDTO(tableReservation);
    }

    boolean isTableAvailable(TableReservationDTO tableReservationDTO, GameTable table) {
        LocalTime startTime = tableReservationDTO.getStartTime();
        LocalTime endTime = tableReservationDTO.getEndTime();
        LocalDate date = tableReservationDTO.getDate();
        TimeSlot timeSlot = table.getTimeSlot();
        LocalDate startDate = timeSlot.getStartDate();
        LocalDate endDate = timeSlot.getEndDate();
        LocalTime programStartTime = timeSlot.getStartTime();
        LocalTime programEndTime = timeSlot.getEndTime();

        if (date.isBefore(startDate) || date.isAfter(endDate) || startTime.isBefore(programStartTime) || endTime.isAfter(programEndTime)) {
            return false;
        }

        List<TableReservation> reservationsForTable = table.getTableReservations();
        for (TableReservation reservation : reservationsForTable) {
            if (reservation.getDate().equals(date)) {
                if (startTime.isBefore(reservation.getEndTime()) && endTime.isAfter(reservation.getStartTime()) ||
                        startTime.equals(reservation.getStartTime()) && endTime.equals(reservation.getEndTime())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public List<TableReservationDTO> getAllTableReservations() {
        List<TableReservation> reservations = tableReservationRepository.findAll();

        if (reservations.isEmpty()) {
            throw new EntityNotFoundException("Oops. There are no reservations here.");
        }
        return reservations.stream()
                .map(TableReservationConverter::EntityToDTO)
                .collect(Collectors.toList());
    }

    private boolean areStartTimeAndEndTimeValid(LocalTime startTime, LocalTime endTime) {
        return startTime.isBefore(endTime.minusHours(1));
    }
}