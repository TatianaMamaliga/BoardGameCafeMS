package com.boardcafe.ms.models.dtos;

import com.boardcafe.ms.models.dtos.enums.GameTableStatusDTO;
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
    private GameTableStatusDTO status;
    private List<EventReservationDTO> reservations;
    private List<GameDTO> games;
}