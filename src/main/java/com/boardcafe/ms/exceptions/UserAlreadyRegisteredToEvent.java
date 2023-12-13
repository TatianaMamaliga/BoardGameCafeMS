package com.boardcafe.ms.exceptions;

public class UserAlreadyRegisteredToEvent extends RuntimeException {
    public UserAlreadyRegisteredToEvent(String message) {
        super(message);
    }
}