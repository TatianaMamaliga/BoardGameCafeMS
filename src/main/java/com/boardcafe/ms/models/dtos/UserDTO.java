package com.boardcafe.ms.models.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Validated
public class UserDTO implements Serializable {
    private Long id;
    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;
    @NotNull(message = "Birth date cannot be null")
    private LocalDate birthDate;
    @NotNull(message = "Email cannot be null")
    private String email;
    @NotNull(message = "Phone number cannot be null")
    private String phoneNumber;
    private List<GameDTO> gameDTOS;
    private List<EventReservationDTO> eventReservationDTOS;
}