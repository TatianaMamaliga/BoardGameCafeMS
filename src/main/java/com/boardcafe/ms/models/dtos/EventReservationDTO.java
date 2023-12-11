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
public class EventReservationDTO implements Serializable {
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReservationStatusDTO reservationStatus;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    private Long eventId;
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}