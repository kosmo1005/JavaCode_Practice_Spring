package com.kulushev.app.exception.auth;

public class TooManyLoginAttemptsException extends RuntimeException {
    private static final String MESSAGE = "Too many failed login attempts.";

    public TooManyLoginAttemptsException() {
        super(MESSAGE);
    }

    public TooManyLoginAttemptsException(String message) {
        super(message);
    }
}
