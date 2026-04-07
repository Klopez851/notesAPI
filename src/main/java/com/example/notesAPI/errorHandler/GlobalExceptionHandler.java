package com.example.notesAPI.errorHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //user input is not valid
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleInvalidUserInput(IllegalArgumentException ex, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setInstance(URI.create(request.getDescription(false)));
        return problemDetail;
    }

    //user already exists
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ProblemDetail handleDuplicateUser(UserAlreadyExistsException ex, WebRequest request){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setInstance(URI.create(request.getDescription(false)));
        return problemDetail;
    }

    //user not found
    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleUserNotFound (UserNotFoundException ex, WebRequest request){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setInstance(URI.create(request.getDescription(false)));
        return problemDetail;
    }

    //Id not found
    @ExceptionHandler(IdNotFoundException.class)
    public ProblemDetail handleIdNotFound(IdNotFoundException ex, WebRequest request){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setInstance(URI.create(request.getDescription(false)));
        return problemDetail;
    }
}
