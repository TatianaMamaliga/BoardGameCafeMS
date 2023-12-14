package com.boardcafe.ms.models.dtos;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Data
@Validated
public class FavoriteGameDTO implements Serializable {
    private Long id;
    private String title;
    private String description;
    private String category;
    private Integer minPlayers;
    private Integer maxPlayers;
}