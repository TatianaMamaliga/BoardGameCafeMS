package com.boardcafe.ms.services;

import com.boardcafe.ms.exceptions.EntityNotFoundException;
import com.boardcafe.ms.exceptions.UserAgeIsInvalidException;
import com.boardcafe.ms.exceptions.UserAlreadyExistsException;
import com.boardcafe.ms.models.dtos.EventReservationDTO;
import com.boardcafe.ms.models.dtos.UserDTO;
import com.boardcafe.ms.models.dtos.enums.ReservationStatusDTO;
import com.boardcafe.ms.models.entities.EventReservation;
import com.boardcafe.ms.models.entities.User;
import com.boardcafe.ms.repositories.UserRepository;
import com.boardcafe.ms.services.util.EventReservationConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final EventReservationConverter eventReservationConverter;

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
                .map(user -> objectMapper.convertValue(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        List<EventReservation> reservations = user.getEventReservations();
        List<EventReservationDTO> reservationDTOs = new ArrayList<>();
        reservations.forEach(reservation -> reservationDTOs.add(eventReservationConverter.EntityToDTO(reservation)));

        UserDTO userDTOResponse = objectMapper.convertValue(user, UserDTO.class);
        userDTOResponse.setEventReservations(reservationDTOs);
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
        List<EventReservationDTO> eventReservationDTOs = new LinkedList<>();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with given id: " + id));
        List<EventReservation> eventReservations = user.getEventReservations();
        eventReservations.forEach(eventReservation -> eventReservationDTOs.add(eventReservationConverter.EntityToDTO(eventReservation)));
        return eventReservationDTOs;
    }

    private boolean isUserAgeValid(LocalDate birthDate) {
        return birthDate.isBefore(LocalDate.now().minusYears(16));
    }
}