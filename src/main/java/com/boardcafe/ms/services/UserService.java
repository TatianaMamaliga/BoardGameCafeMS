package com.boardcafe.ms.services;

import com.boardcafe.ms.models.dtos.EventReservationDTO;
import com.boardcafe.ms.models.dtos.TableReservationDTO;
import com.boardcafe.ms.models.dtos.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    void deleteUserById(Long id);

    void addGameToFavorites(Long userId, Long gameId);

    List<EventReservationDTO> getEventReservationsByUserId(Long id);

    List<TableReservationDTO> getTableReservationsByUserId(Long id);
}