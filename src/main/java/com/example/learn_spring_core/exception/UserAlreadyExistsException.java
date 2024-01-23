package com.example.learn_spring_core.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("It is not possible to register a new trainee/ trainer because a trainer/ trainee with this data already exists.");
    }
}
