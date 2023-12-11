package com.boardcafe.ms.models.dtos;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Embeddable
@Data
public class TimeSlotDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
}