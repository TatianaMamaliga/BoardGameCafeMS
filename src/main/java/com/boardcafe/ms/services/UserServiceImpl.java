package com.boardcafe.ms.services;

import com.boardcafe.ms.exceptions.EntityNotFoundException;
import com.boardcafe.ms.models.dtos.EventReservationDTO;
import com.boardcafe.ms.models.dtos.UserDTO;
import com.boardcafe.ms.models.entities.User;
import com.boardcafe.ms.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User userEntity = objectMapper.convertValue(userDTO, User.class);
        User savedUserEntity = userRepository.save(userEntity);
        UserDTO userDTOResponse = objectMapper.convertValue(savedUserEntity, UserDTO.class);
        return userDTOResponse;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        if (allUsers.isEmpty()) {
            throw new EntityNotFoundException("Oops. There are no users in the database.");
        }
        return allUsers.stream()
                .map(user -> objectMapper.convertValue(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        UserDTO userDTOResponse = objectMapper.convertValue(user, UserDTO.class);
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
    public List<EventReservationDTO> getReservationsByUserId(Long id) {
        return null;
    }
}