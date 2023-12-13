package com.boardcafe.ms.models.dtos;

import com.boardcafe.ms.models.entities.GameTable;
import com.boardcafe.ms.models.entities.User;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.Set;

@Data
@Validated
public class GameDTO implements Serializable {

    private Long id;
    @NotNull(message = "Title cannot be null")
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
    private String title;

    @NotNull(message = "Description cannot be null")
    @Size(min = 1, max = 500, message = "Description must be between 1 and 500 characters")
    private String description;

    @NotNull(message = "Category cannot be null")
    @Size(min = 1, max = 50, message = "Category must be between 1 and 50 characters")
    private String category;

    @NotNull(message = "Quantity cannot be null")
    @PositiveOrZero(message = "Quantity must be a positive number or zero")
    private Integer quantity;

    @NotNull(message = "Price cannot be null")
    @PositiveOrZero(message = "Price must be a positive number or zero")
    private Double price;

    @NotNull(message = "Minimum players cannot be null")
    @Min(value = 1, message = "Minimum players must be at least 1")
    private Integer minPlayers;

    @NotNull(message = "Maximum players cannot be null")
    @Min(value = 1, message = "Maximum players must be at least 1")
    private Integer maxPlayers;

    private Set<GameTableDTO> gameTables;
    private Set<UserDTO> users;
}