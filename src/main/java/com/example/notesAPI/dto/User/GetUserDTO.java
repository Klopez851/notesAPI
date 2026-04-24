package com.example.notesAPI.dto.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserDTO {

    private String email;

    @JsonIgnore
    public boolean isValid(){
        if(email == null || email.isBlank()){
            return false;
        }
        return true;
    }

}
