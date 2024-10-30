package com.incudo.exception;

public class UserNotFoundException extends BaseException{
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(int userId) {
        super(String.format("Utente con ID " + userId + " non trovato!"));
    }
}
