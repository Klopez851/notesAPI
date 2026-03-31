package com.example.notesAPI.dto.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserLoginDTO {

    private String email;
    private String userPassword;

    public boolean isValid(){
        if((email == null || email.isBlank()) ||
                (userPassword == null || userPassword.isBlank())) {
            return false;
        }
        return true;
    }
}
