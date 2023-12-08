package com.boardcafe.ms.models.dtos;

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
    private List<ReservationDTO> reservations;
    private List<GameDTO> games;
}