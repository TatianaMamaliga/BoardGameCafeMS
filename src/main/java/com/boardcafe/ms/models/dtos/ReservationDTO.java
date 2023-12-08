package com.boardcafe.ms.models.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Validated
public class ReservationDTO implements Serializable {

    private Long id;
    @NotNull(message = "Date cannot be null")
    @FutureOrPresent(message = "Date must be in the present or future")
    private LocalDate date;

    @NotNull(message = "Start time cannot be null")
    private LocalTime startTime;

    @NotNull(message = "End time cannot be null")
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private ReservationStatusDTO reservationStatus;

    @NotNull(message = "Number of attendees cannot be null")
    @Min(value = 0, message = "Number of attendees cannot be negative")
    private Integer attendees;

    @NotNull(message = "Event ID cannot be null")
    private Long eventId;
    @NotNull(message = "Game table ID cannot be null")
    private Long gameTableId;
    @NotNull(message = "User ID cannot be null")
    private Long userId;
}