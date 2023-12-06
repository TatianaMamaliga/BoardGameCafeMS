package com.boardcafe.ms.services;

import com.boardcafe.ms.models.dtos.ReservationDTO;
import com.boardcafe.ms.models.dtos.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        return null;
    }

    @Override
    public void deleteUserById(Long id) {

    }

    @Override
    public List<ReservationDTO> getReservationsByUserId(Long id) {
        return null;
    }
}