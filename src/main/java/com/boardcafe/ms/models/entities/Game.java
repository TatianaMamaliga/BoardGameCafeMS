package com.boardcafe.ms.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "title", unique = true) // game title must be unique
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "category")
    private String category;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "price")
    private Double price;
    @Column(name = "min_players")
    private Integer minPlayers;
    @Column(name = "max_players")
    private Integer maxPlayers;

    @ManyToMany(mappedBy = "games")
    private Set<GameTable> gameTables = new HashSet<>();

    @ManyToMany(mappedBy = "games")
    @JsonIgnore
    private Set<User> users = new HashSet<>();
}