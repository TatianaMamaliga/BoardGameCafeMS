package com.boardcafe.ms.models.dtos;

import com.boardcafe.ms.models.entities.GameTableStatus;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.List;

@Data
@Validated
public class GameTableDTO implements Serializable {
    private Long id;
    private Integer capacity;
    private GameTableStatus status;
    private List<ReservationDTO> reservationDTOS;
    private List<GameDTO> gameDTOS;
}