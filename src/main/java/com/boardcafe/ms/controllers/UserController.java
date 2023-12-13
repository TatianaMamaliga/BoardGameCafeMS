package com.boardcafe.ms.controllers;

import com.boardcafe.ms.models.dtos.EventReservationDTO;
import com.boardcafe.ms.models.dtos.UserDTO;
import com.boardcafe.ms.models.dtos.enums.ReservationStatusDTO;
import com.boardcafe.ms.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDTO));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/{id}/event-reservations")
    public ResponseEntity<List<EventReservationDTO>> getEventReservationsByUserId(@PathVariable long id) {
        return ResponseEntity.ok(userService.getReservationsByUserId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable long id) {
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted user with id: " + id);
    }
}