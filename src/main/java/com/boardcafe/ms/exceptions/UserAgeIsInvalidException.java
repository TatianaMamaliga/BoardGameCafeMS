package com.boardcafe.ms.exceptions;

public class UserAgeIsInvalidException extends RuntimeException {
    public UserAgeIsInvalidException(String message) {
        super(message);
    }
}