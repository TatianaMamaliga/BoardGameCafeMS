package com.boardcafe.ms.models.dtos;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Validated
public class UserDTO implements Serializable {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private List<GameDTO> gameDTOS;
    private List<ReservationDTO> reservationDTOS;
}