package com.incudo.exception;

public class UserAlreadyExist extends BaseException {
    public UserAlreadyExist(String message) {
        super(message);
    }

    public UserAlreadyExist(int userId) {
        super(String.format("Utente con ID " + userId + " è già esistente!"));
    }
}
