package com.boardcafe.ms.exceptions;

public class EventCapacityNotAvailableException extends RuntimeException {
    public EventCapacityNotAvailableException(String message) {
        super(message);
    }
}