package com.boardcafe.ms.services;

import com.boardcafe.ms.models.dtos.ReservationDTO;
import com.boardcafe.ms.models.dtos.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    void deleteUserById(Long id);

    List<ReservationDTO> getReservationsByUserId(Long id);
}