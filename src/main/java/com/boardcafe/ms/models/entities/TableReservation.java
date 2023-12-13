package com.boardcafe.ms.models.entities;

import com.boardcafe.ms.models.entities.enums.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "table_reservations")
public class TableReservation extends Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attendees")
    private Integer attendees;

    @Builder
    public TableReservation(LocalDate date, LocalTime startTime, LocalTime endTime, ReservationStatus status, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        super(date, startTime, endTime, status, createdAt, modifiedAt);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id")
    @JsonIgnore
    private GameTable gameTable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}