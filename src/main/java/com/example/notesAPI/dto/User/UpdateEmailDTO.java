package com.example.notesAPI.dto.User;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateEmailDTO {

    private final String oldEmail;
    private String newEmail;

    @JsonIgnore
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

