package com.boardcafe.ms.models.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;


@Entity(name = "Event")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private LocalDate date;
    @Column
    private LocalTime startTime;
    @Column(columnDefinition = "interval")
    private Duration duration;
    @Column
    private Integer capacity;

    @OneToMany(mappedBy = "event")
    private Set<Reservation> reservationList = new HashSet<>();
}