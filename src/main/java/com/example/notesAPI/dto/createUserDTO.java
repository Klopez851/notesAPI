package com.example.notesAPI.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class createUserDTO {
    private String username;
    private String passwordHash;

    public createUserDTO(){}
}
