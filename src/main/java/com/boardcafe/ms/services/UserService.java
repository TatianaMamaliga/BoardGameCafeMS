package com.boardcafe.ms.services;

import com.boardcafe.ms.models.dtos.EventReservationDTO;
import com.boardcafe.ms.models.dtos.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    void deleteUserById(Long id);

    List<EventReservationDTO> getReservationsByUserId(Long id);
}