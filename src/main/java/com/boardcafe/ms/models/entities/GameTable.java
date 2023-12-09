package com.boardcafe.ms.models.entities;

import com.boardcafe.ms.models.entities.enums.GameTableStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column
    private Integer capacity;
    @Column
    @Enumerated(EnumType.STRING)
    private GameTableStatus status;

    @OneToMany(mappedBy = "gameTable", fetch = FetchType.LAZY)
    private List<EventReservation> eventReservations = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "game_table",
            joinColumns = @JoinColumn(name = "table_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private Set<Game> games = new HashSet<>();
}