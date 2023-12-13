package com.boardcafe.ms.exceptions;

public class EventTimeSlotNotAvailableException extends RuntimeException {
    public EventTimeSlotNotAvailableException(String message) {
        super(message);
    }
}