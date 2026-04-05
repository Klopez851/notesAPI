package com.example.notesAPI.dto.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserDTO {

    private String email;

    public boolean isValid(){
        if(email == null || email.isBlank()){
            return false;
        }
        return true;
    }

}
