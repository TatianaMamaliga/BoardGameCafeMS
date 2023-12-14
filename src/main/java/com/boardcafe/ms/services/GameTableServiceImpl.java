package com.boardcafe.ms.services;

import com.boardcafe.ms.exceptions.EntityNotFoundException;
import com.boardcafe.ms.models.dtos.GameTableDTO;
import com.boardcafe.ms.models.dtos.EventReservationDTO;
import com.boardcafe.ms.models.dtos.TableReservationDTO;
import com.boardcafe.ms.models.dtos.TimeSlotDTO;
import com.boardcafe.ms.models.entities.*;
import com.boardcafe.ms.models.entities.enums.GameTableStatus;
import com.boardcafe.ms.repositories.GameTableRepository;
import com.boardcafe.ms.services.util.TableReservationConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameTableServiceImpl implements GameTableService {
    private final GameTableRepository gameTableRepository;
    private final ObjectMapper objectMapper;
    private final TableReservationConverter tableReservationConverter;

    @Override
    public GameTableDTO createTable(GameTableDTO gameTableDTO) {
        TimeSlotDTO timeSlotDTO = gameTableDTO.getTimeSlot();
        TimeSlot timeSlotEntity = convertTimeSlotToEntity(timeSlotDTO);
        GameTable gameTableEntity = objectMapper.convertValue(gameTableDTO, GameTable.class);

        gameTableEntity.setStatus(GameTableStatus.AVAILABLE);
        gameTableEntity.setTimeSlot(timeSlotEntity);

        GameTable savedGameTableEntity = gameTableRepository.save(gameTableEntity);

        TimeSlotDTO timeSlotDTOResponse = covertTimeSlotToDTO(savedGameTableEntity.getTimeSlot());
        GameTableDTO gameTableDTOResponse = objectMapper.convertValue(savedGameTableEntity, GameTableDTO.class);
        gameTableDTOResponse.setTimeSlot(timeSlotDTOResponse);
        return gameTableDTOResponse;
    }

    @Override
    public GameTableDTO getTableById(Long id) {
        GameTable gameTable = gameTableRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Game table not found with id: " + id));

        List<TableReservation> reservations = gameTable.getTableReservations();
        List<TableReservationDTO> reservationDTOS = reservations.stream()
                .map(TableReservationConverter::EntityToDTO)
                .collect(Collectors.toList());

        GameTableDTO gameTableDTOResponse = objectMapper.convertValue(gameTable, GameTableDTO.class);
        gameTableDTOResponse.setReservations(reservationDTOS);

        return gameTableDTOResponse;
    }

    @Override
    public List<GameTableDTO> getAllGameTables() {
        List<GameTable> allGameTables = gameTableRepository.findAll();
        if (allGameTables.isEmpty()) {
            throw new EntityNotFoundException("Oops. There are no game tables registered.");
        }
        return allGameTables.stream()
                .map(game -> objectMapper.convertValue(game, GameTableDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<EventReservationDTO> getReservationsByTableId(Long tableId) {
        return null;
    }

    @Override
    public void deleteTableById(Long id) {
        try {
            GameTable gameTable = gameTableRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Game table not found with id: " + id));
            gameTableRepository.delete(gameTable);
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }

    private TimeSlot convertTimeSlotToEntity(TimeSlotDTO timeSlotDTO) {
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setStartDate(timeSlotDTO.getStartDate());
        timeSlot.setEndDate(timeSlotDTO.getEndDate());
        timeSlot.setStartTime(timeSlotDTO.getStartTime());
        timeSlot.setEndTime(timeSlotDTO.getEndTime());
        return timeSlot;
    }

    private TimeSlotDTO covertTimeSlotToDTO(TimeSlot timeSlot) {
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO();
        timeSlotDTO.setStartDate(timeSlot.getStartDate());
        timeSlotDTO.setEndDate(timeSlot.getEndDate());
        timeSlotDTO.setStartTime(timeSlot.getStartTime());
        timeSlotDTO.setEndTime(timeSlot.getEndTime());
        return timeSlotDTO;
    }
}