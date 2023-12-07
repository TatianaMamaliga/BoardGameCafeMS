package com.boardcafe.ms.models.dtos;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.List;

@Data
@Validated
public class GameTableDTO implements Serializable {
    private Long id;
    private Integer capacity;
    private GameTableStatusDTO status;
    private List<ReservationDTO> reservationDTOS;
    private List<GameDTO> gameDTOS;
}