package com.kulushev.app.exception.auth;

public class AuthenticationFailedException extends RuntimeException {
    private static final String MESSAGE = "Invalid credentials.";

    public AuthenticationFailedException() {
        super(MESSAGE);
    }

    public AuthenticationFailedException(String message) {
        super(message);
    }
}
