package com.example.notesAPI.dto.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoDTO {

    private String username;
    private String email;
    private String userPassword;

    @JsonIgnore //jackson will automatically make add an unwanted "valid" field to the json bc of this method w/o the annotation
    public boolean isValid(){
        if((username == null || username.isBlank()) ||
                (email == null || email.isBlank()) ||
                (userPassword == null || userPassword.isBlank())) {
            return false;
        }
        return true;
    }
}
