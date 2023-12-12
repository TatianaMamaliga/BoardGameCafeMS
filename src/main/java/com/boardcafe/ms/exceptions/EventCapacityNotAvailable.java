package com.boardcafe.ms.exceptions;

public class EventCapacityNotAvailable extends RuntimeException {
    public EventCapacityNotAvailable(String message) {
        super(message);
    }
}