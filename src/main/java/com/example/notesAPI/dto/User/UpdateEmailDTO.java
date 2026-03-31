package com.example.notesAPI.dto.User;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateEmailDTO {

    private final String oldEmail;
    private String newEmail;

    public boolean isValid(){
        if((newEmail == null || newEmail.isBlank())
                ||(oldEmail == null || oldEmail.isBlank())){
            return false;
        }
        if (newEmail.equals(oldEmail)){
            return false;
        }
        return true;
    }
}

