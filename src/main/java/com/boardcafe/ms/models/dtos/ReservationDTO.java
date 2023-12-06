package com.boardcafe.ms.models.dtos;

import com.boardcafe.ms.models.entities.GameTable;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Validated
public class ReservationDTO implements Serializable {

    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private EventDTO eventDTO;
    private GameTableDTO gameTableDTO;
    private UserDTO userDTO;
}