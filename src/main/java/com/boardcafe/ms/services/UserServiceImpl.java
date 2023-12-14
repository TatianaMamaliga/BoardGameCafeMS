package com.boardcafe.ms.services;

import com.boardcafe.ms.exceptions.EntityNotFoundException;
import com.boardcafe.ms.exceptions.GameIsAlreadyInFavoritesException;
import com.boardcafe.ms.exceptions.UserAgeIsInvalidException;
import com.boardcafe.ms.exceptions.UserAlreadyExistsException;
import com.boardcafe.ms.models.dtos.*;
import com.boardcafe.ms.models.dtos.enums.ReservationStatusDTO;
import com.boardcafe.ms.models.entities.EventReservation;
import com.boardcafe.ms.models.entities.Game;
import com.boardcafe.ms.models.entities.TableReservation;
import com.boardcafe.ms.models.entities.User;
import com.boardcafe.ms.repositories.GameRepository;
import com.boardcafe.ms.repositories.UserRepository;
import com.boardcafe.ms.services.util.EventReservationConverter;
import com.boardcafe.ms.services.util.TableReservationConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final EventReservationConverter eventReservationConverter;
    private final GameRepository gameRepository;
    private final TableReservationConverter tableReservationConverter;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if (!isUserAgeValid(userDTO.getBirthDate())) {
            throw new UserAgeIsInvalidException("User must be at least 16");
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("User already exists");
        }
        User userEntity = objectMapper.convertValue(userDTO, User.class);
        User savedUserEntity = userRepository.save(userEntity);
        return objectMapper.convertValue(savedUserEntity, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        if (allUsers.isEmpty()) {
            throw new EntityNotFoundException("Oops. There are no users in the database.");
        }
        return allUsers.stream()
                .map(this::getUserDTOResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        return getUserDTOResponse(user);
    }

    private UserDTO getUserDTOResponse(User user) {
        List<EventReservation> reservations = user.getEventReservations();
        List<EventReservationDTO> reservationDTOs = new ArrayList<>();
        List<FavoriteGameDTO> favoriteGameDTOS = convertGameEntityToDTO(user.getGames());
        reservations.forEach(reservation -> reservationDTOs.add(eventReservationConverter.EntityToDTO(reservation)));

        UserDTO userDTOResponse = objectMapper.convertValue(user, UserDTO.class);
        userDTOResponse.setEventReservations(reservationDTOs);
        userDTOResponse.setGames(favoriteGameDTOS);
        return userDTOResponse;
    }

    @Override
    public void deleteUserById(Long id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
            userRepository.delete(user);
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }

    @Override
    public void addGameToFavorites(Long userId, Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found with the given id: " + gameId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with the given id: " + userId));

        if (isGameAlreadyInFavorites(game, user)) {
            throw new GameIsAlreadyInFavoritesException("Selected game is already in your favorites list.");
        }
        user.getGames().add(game);
        userRepository.save(user);
        game.getUsers().add(user);
        gameRepository.save(game);
    }

    @Override
    public List<EventReservationDTO> getEventReservationsByUserId(Long id) {
        List<EventReservationDTO> eventReservationDTOs = new LinkedList<>();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with given id: " + id));
        List<EventReservation> eventReservations = user.getEventReservations();
        eventReservations.forEach(eventReservation -> eventReservationDTOs.add(eventReservationConverter.EntityToDTO(eventReservation)));
        return eventReservationDTOs;
    }

    @Override
    public List<TableReservationDTO> getTableReservationsByUserId(Long id) {
        List<TableReservationDTO> tableReservationDTOS = new LinkedList<>();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with given id: " + id));
        List<TableReservation> tableReservations = user.getTableReservations();
        tableReservations.forEach(tableReservation -> tableReservationDTOS.add(TableReservationConverter.EntityToDTO(tableReservation)));
        return tableReservationDTOS;
    }

    private boolean isUserAgeValid(LocalDate birthDate) {
        return birthDate.isBefore(LocalDate.now().minusYears(16));
    }

    private boolean isGameAlreadyInFavorites(Game game, User user) {
        Set<Game> games = user.getGames();
        return games.contains(game);
    }

    private List<FavoriteGameDTO> convertGameEntityToDTO(Set<Game> games) {
        List<FavoriteGameDTO> gameDTOS = new ArrayList<>();
        FavoriteGameDTO gameDTO = new FavoriteGameDTO();
        games.forEach(game -> {
            gameDTO.setId(game.getId());
            gameDTO.setTitle(game.getTitle());
            gameDTO.setCategory(game.getCategory());
            gameDTO.setDescription(game.getDescription());
            gameDTO.setMinPlayers(game.getMinPlayers());
            gameDTO.setMaxPlayers(game.getMaxPlayers());
            gameDTOS.add(gameDTO);
        });
        return gameDTOS;
    }
}