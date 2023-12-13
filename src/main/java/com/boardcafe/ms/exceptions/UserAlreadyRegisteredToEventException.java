package com.boardcafe.ms.exceptions;

public class UserAlreadyRegisteredToEventException extends RuntimeException {
    public UserAlreadyRegisteredToEventException(String message) {
        super(message);
    }
}