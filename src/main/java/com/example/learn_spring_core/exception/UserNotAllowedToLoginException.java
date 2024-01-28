package com.example.learn_spring_core.exception;

public class UserNotAllowedToLoginException extends RuntimeException {

    public UserNotAllowedToLoginException(String userName) {
        super(userName);
    }

}
