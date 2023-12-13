package com.boardcafe.ms.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Embeddable
public class TimeSlot {
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "start_time")
    private LocalTime startTime; // example: 09:00
    @Column(name = "end_time")
    private LocalTime endTime; // example: 22:00
}