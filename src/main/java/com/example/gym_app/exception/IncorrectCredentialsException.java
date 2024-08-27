package com.example.gym_app.exception;

public class IncorrectCredentialsException extends RuntimeException {

    public IncorrectCredentialsException() {
        super("Entered credentials are incorrect");
    }
}
