package com.example.learn_spring_core.controller;

import com.example.learn_spring_core.exception.IncorrectCredentialsException;
import com.example.learn_spring_core.exception.UserAlreadyExistsException;
import com.example.learn_spring_core.exception.UserNotAllowedToLoginException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    protected ResponseEntity<String> handleEntityNotFoundEx(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler({UserAlreadyExistsException.class})
    protected ResponseEntity<String> handleUserAlreadyExistsEx(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler({IncorrectCredentialsException.class})
    protected ResponseEntity<String> handleIncorrectCredentialsEx(IncorrectCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bad credentials for" + ex.getMessage());
    }

    @ExceptionHandler({UserNotAllowedToLoginException.class})
    protected ResponseEntity<String> handleUserNotAllowedToLoginEx(UserNotAllowedToLoginException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

}
