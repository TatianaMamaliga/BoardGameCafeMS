package com.boardcafe.ms.exceptions;

public class GameIsAlreadyInFavoritesException extends RuntimeException {
    public GameIsAlreadyInFavoritesException(String message) {
        super(message);
    }
}