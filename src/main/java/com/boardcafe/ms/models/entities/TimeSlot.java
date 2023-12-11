package com.boardcafe.ms.models.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Embeddable
public class TimeSlot {
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime; // 09:00
    private LocalTime endTime; // 22:00
}