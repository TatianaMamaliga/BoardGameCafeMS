package com.boardcafe.ms.models.dtos;

import com.boardcafe.ms.models.dtos.enums.GameTableStatusDTO;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.List;

@Data
@Validated
public class GameTableDTO implements Serializable {
    private Long id;
    @NotNull
    private Integer capacity;
    @Embedded
    private TimeSlotDTO timeSlot;

    private GameTableStatusDTO status;
    private List<TableReservationDTO> reservations;
    private List<GameDTO> games;
}