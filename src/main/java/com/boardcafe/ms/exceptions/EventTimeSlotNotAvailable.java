package com.boardcafe.ms.exceptions;

public class EventTimeSlotNotAvailable extends RuntimeException {
    public EventTimeSlotNotAvailable(String message) {
        super(message);
    }
}