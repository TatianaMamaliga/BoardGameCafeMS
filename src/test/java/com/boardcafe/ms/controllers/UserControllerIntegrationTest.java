package com.boardcafe.ms.controllers;


import com.boardcafe.ms.models.dtos.UserDTO;
import com.boardcafe.ms.services.UserService;
import com.boardcafe.ms.util.UserTestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("When adding a new user, return status code 201.")
    void whenCreateUserTest() throws Exception {
        String userJson = UserTestUtil.getUserJson();

        UserDTO mockUserDTO = UserTestUtil.createUserDTO();
        BDDMockito.given(userService.createUser(ArgumentMatchers.any(UserDTO.class))).willReturn(mockUserDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("jane@email.com")).andReturn();
    }

    @Test
    @DisplayName("When getting the user list, return status code 200.")
    void whenGetUserList() throws Exception {
        UserDTO userDTO = UserTestUtil.createUserDTO();
        List<UserDTO> userDTOList = List.of(userDTO);

        BDDMockito.given(userService.getAllUsers()).willReturn(userDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value(userDTO.getName()));
    }
}