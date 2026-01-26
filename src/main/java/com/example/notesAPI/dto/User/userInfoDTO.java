package com.example.notesAPI.dto.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class userInfoDTO {
    private String username;
    private String email;
    private String passwordHash;

    public userInfoDTO(){}
}
