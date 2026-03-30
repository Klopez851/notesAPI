package com.example.notesAPI.dto.User;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDTO {
    private String username;
    private String email;
    private String userPassword;

    public UserInfoDTO(){}

    public boolean isValid(){
        if((username == null || username.isBlank()) ||
                (email == null || email.isBlank()) ||
                (userPassword == null || userPassword.isBlank())) {
            return false;
        }
        return true;
    }
}
