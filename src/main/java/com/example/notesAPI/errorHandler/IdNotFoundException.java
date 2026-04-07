package com.example.notesAPI.errorHandler;

public class IdNotFoundException extends RuntimeException{
    public IdNotFoundException(String message){
        super(message);
    }
}
