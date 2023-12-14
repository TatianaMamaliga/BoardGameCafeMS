package com.boardcafe.ms.exceptions;

public class StartTimeAndEndTimeAreInvalidException extends RuntimeException {
    public StartTimeAndEndTimeAreInvalidException(String message) {
        super(message);
    }
}