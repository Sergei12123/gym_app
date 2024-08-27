package com.example.gym_app.exception;

public class UserNotAllowedToLoginException extends RuntimeException {

    public UserNotAllowedToLoginException(String userName) {
        super(userName);
    }

}
