package com.kulushev.app.exception;

public class UserAlreadyExist extends RuntimeException {
    private static final String MESSAGE = "User is already exist";

    public UserAlreadyExist() {
        super(MESSAGE);
    }

    public UserAlreadyExist(String message) {
        super(message);
    }

}
