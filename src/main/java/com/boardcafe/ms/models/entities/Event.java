package com.boardcafe.ms.models.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column
    private LocalTime endTime;
    @Column
    private Integer capacity;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private Set<EventReservation> eventReservations = new HashSet<>();
}