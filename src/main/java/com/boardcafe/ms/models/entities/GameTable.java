package com.boardcafe.ms.models.entities;

import com.boardcafe.ms.models.dtos.TableReservationDTO;
import com.boardcafe.ms.models.entities.enums.GameTableStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tables")
public class GameTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "capacity")
    private Integer capacity;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private GameTableStatus status;
    @Embedded
    private TimeSlot timeSlot;

    @OneToMany(mappedBy = "gameTable", fetch = FetchType.LAZY)
    private List<TableReservation> tableReservations = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "game_table",
            joinColumns = @JoinColumn(name = "table_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private Set<Game> games = new HashSet<>();
}