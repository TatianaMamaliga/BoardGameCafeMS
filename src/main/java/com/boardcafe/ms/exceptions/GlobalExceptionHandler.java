package com.boardcafe.ms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEventNotFoundException(EntityNotFoundException exception, WebRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                exception.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TableNotAvailableException.class)
    public ResponseEntity<ApiErrorResponse> handleTableNotAvailableException(TableNotAvailableException exception, WebRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                exception.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EventCapacityNotAvailableException.class)
    public ResponseEntity<ApiErrorResponse> handleEventCapacityNotAvailable(EventCapacityNotAvailableException exception, WebRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                exception.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EventTimeSlotNotAvailableException.class)
    public ResponseEntity<ApiErrorResponse> handleEventTimeSlotNotAvailable(EventTimeSlotNotAvailableException exception, WebRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                exception.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserAlreadyRegisteredToEventException.class)
    public ResponseEntity<ApiErrorResponse> handleUserAlreadyRegisteredToEvent(UserAlreadyRegisteredToEventException exception, WebRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                exception.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException exception, WebRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                exception.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.CONFLICT);
    }
}