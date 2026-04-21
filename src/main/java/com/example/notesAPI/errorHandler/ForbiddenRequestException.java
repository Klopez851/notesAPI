package com.example.notesAPI.errorHandler;

public class ForbiddenRequestException extends RuntimeException {
    public ForbiddenRequestException(String message) {
        super(message);
    }
}
