package com.boardcafe.ms.util;

import com.boardcafe.ms.models.dtos.UserDTO;
import com.boardcafe.ms.models.entities.User;

import java.time.LocalDate;

public class UserTestUtil {

    private UserTestUtil() {
    }

    public static UserDTO createUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Jane Done");
        userDTO.setBirthDate(LocalDate.of(2000, 1, 1));
        userDTO.setEmail("jane@email.com");
        userDTO.setPhoneNumber("0712345678");
        return userDTO;
    }

    public static User createUserEntity() {
        User user = new User();
        user.setName("Jane Doe");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        user.setEmail("jane@email.com");
        user.setPhoneNumber("0712345678");
        return user;
    }

    public static String getUserJson() {
        return """
                {
                    "name": "Jane Doe",
                    "birthDate": "2000-01-01",
                    "email": "jame@email.com",
                    "phoneNumber": "0712345678"
                }
                """;
    }
}