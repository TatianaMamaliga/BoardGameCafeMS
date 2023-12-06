package com.boardcafe.ms.services;

import com.boardcafe.ms.models.dtos.GameDTO;

import java.util.List;

public interface GameService {

    GameDTO createGame(GameDTO gameDTO);

    GameDTO getGameById(Long id);

    List<GameDTO> getAllGames();

    GameDTO updateGame(Long id, GameDTO gameDTO);

    void deleteGameById(Long id);
}