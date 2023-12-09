package com.boardcafe.ms.models.dtos;

import jakarta.validation.constraints.*;
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

    @NotNull(message = "Title cannot be null")
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
    private String title;

    @NotNull(message = "Date cannot be null")
    @Future(message = "Date must be in the future")
    private LocalDate date;

    @NotNull(message = "Start time cannot be null")
    private LocalTime startTime;

    @NotNull(message = "End time cannot be null")
    private LocalTime endTime;

    @NotNull(message = "Capacity cannot be null")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    private Set<EventReservationDTO> reservations;
}