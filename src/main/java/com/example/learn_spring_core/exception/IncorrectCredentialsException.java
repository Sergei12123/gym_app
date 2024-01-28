package com.example.learn_spring_core.exception;

public class IncorrectCredentialsException extends RuntimeException {

    public IncorrectCredentialsException() {
        super("Entered credentials are incorrect");
    }

    public IncorrectCredentialsException(String userName) {
        super(userName);
    }

}
