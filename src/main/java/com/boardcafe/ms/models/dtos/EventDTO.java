package com.boardcafe.ms.models.dtos;

import com.boardcafe.ms.models.entities.Reservation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
@Validated
public class EventDTO implements Serializable {
    private Long id;

    private String title;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer capacity;

    private Set<Reservation> reservations;
}