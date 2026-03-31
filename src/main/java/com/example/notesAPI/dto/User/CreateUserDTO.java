package com.example.notesAPI.dto.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateUserDTO {

    private String username;
    private String email;
    private String userPassword;

    public boolean isValid(){
        if((username == null || username.isBlank()) ||
                (email == null || email.isBlank()) ||
                (userPassword == null || userPassword.isBlank())) {
            return false;
        }
        return true;
    }
}
