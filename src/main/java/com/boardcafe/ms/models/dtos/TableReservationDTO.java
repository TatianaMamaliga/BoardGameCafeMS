package com.boardcafe.ms.models.dtos;

import com.boardcafe.ms.models.dtos.enums.ReservationStatusDTO;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
public class TableReservationDTO implements Serializable {

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

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    private Long gameTableId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}