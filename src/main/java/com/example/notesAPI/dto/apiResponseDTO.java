package com.example.notesAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//T = type, a generic placeholder
public class apiResponseDTO<T> {

    private boolean response;
    private String message;
    private T data;

    public apiResponseDTO(){}
}
