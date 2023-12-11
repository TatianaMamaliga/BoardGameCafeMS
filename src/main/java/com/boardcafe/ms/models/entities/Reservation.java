package com.boardcafe.ms.models.entities;

import com.boardcafe.ms.models.entities.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class Reservation {
    @Column
    private LocalDate date;
    @Column
    private LocalTime startTime;
    @Column
    private LocalTime endTime;
    @Column
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime modifiedAt;
}