package com.boardcafe.ms.models.dtos;

import com.boardcafe.ms.models.entities.GameTable;
import com.boardcafe.ms.models.entities.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.Set;

@Data
@Validated
public class GameDTO implements Serializable {

    private Long id;
    private String title;
    private String description;
    private String category;
    private Integer quantity;

    private Integer price;
    private Integer minPlayers;
    private Integer maxPlayers;

    private Set<GameTable> gameTables;
    private Set<User> users;
}