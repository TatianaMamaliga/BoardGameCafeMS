package com.boardcafe.ms.services;

import com.boardcafe.ms.exceptions.EntityNotFoundException;
import com.boardcafe.ms.models.dtos.GameDTO;
import com.boardcafe.ms.models.entities.Game;
import com.boardcafe.ms.repositories.GameRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final ObjectMapper objectMapper;

    @Override
    public GameDTO createGame(GameDTO gameDTO) {
        Game gameEntity = objectMapper.convertValue(gameDTO, Game.class);
        Game savedGameEntity = gameRepository.save(gameEntity);
        GameDTO gameDTOResponse = objectMapper.convertValue(savedGameEntity, GameDTO.class);
        return gameDTOResponse;
    }

    @Override
    public GameDTO getGameById(Long id) {
        Game gameEntity = gameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Game not found with given id: " + id));
        GameDTO gameDTOResponse = objectMapper.convertValue(gameEntity, GameDTO.class);
        return gameDTOResponse;
    }

    @Override
    public List<GameDTO> getAllGames() {
        List<GameDTO> allGamesDTO = new ArrayList<>();
        List<Game> allGames = gameRepository.findAll();
        for (Game game : allGames) {
            GameDTO gameDTO = objectMapper.convertValue(game, GameDTO.class);
            allGamesDTO.add(gameDTO);
        }
        return allGamesDTO;
    }

    @Override
    public GameDTO updateGame(Long id, GameDTO gameDTO) {
        return null;
    }

    @Override
    public void deleteGameById(Long id) {
        try {
            gameRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Game not found with id: " + id));
            gameRepository.deleteById(id);
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }
}