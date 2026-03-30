package com.example.notesAPI.errorHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends Exception{

    //user input is not valid
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleInvalidUserInput(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    //user already exists
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleDuplicateUser(UserAlreadyExistsException ex){
        //throw a Http.CONFLICT status
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleInvalidUsername(UsernameNotFoundException ex){
        //Throw Http.CONFLICt status
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound (UserNotFoundException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
