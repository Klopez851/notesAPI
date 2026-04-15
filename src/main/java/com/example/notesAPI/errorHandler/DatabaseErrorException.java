package com.example.notesAPI.errorHandler;

public class DatabaseErrorException extends RuntimeException {
    public DatabaseErrorException(String message) {
        super(message);
    }
}
