package com.boardcafe.ms.services;

import com.boardcafe.ms.exceptions.UserAlreadyExistsException;
import com.boardcafe.ms.models.dtos.UserDTO;
import com.boardcafe.ms.models.entities.User;
import com.boardcafe.ms.repositories.UserRepository;
import com.boardcafe.ms.util.UserTestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    @DisplayName("User created successfully")
    void createUserTestShouldPass() {
        //given
        UserDTO userDTO = UserTestUtil.createUserDTO();
        User userEntity = UserTestUtil.createUserEntity();

        when(objectMapper.convertValue(userDTO, User.class)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(objectMapper.convertValue(userEntity, UserDTO.class)).thenReturn(userDTO);

        //when
        UserDTO userDTOResponse = userServiceImpl.createUser(userDTO);

        //then
        assertEquals(userDTOResponse, userDTO);
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    @DisplayName("User already exists exception")
    void testUserAlreadyExistsShouldFail() {
        UserDTO userDTO = UserTestUtil.createUserDTO();
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(true);
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userServiceImpl.createUser(userDTO));
    }
}