package com.boardcafe.ms.models.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "Game")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String category;
    @Column
    private Integer quantity;
    @Column
    private Long price;
    @Column
    private Integer minPlayers;
    @Column
    private Integer maxPlayers;

    @ManyToMany(mappedBy = "games")
    private Set<GameTable> gameTables = new HashSet<>();

    @ManyToMany(mappedBy = "games")
    private Set<User> users = new HashSet<>();
}