package com.example.notesAPI.dto.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateUserInfoDTO {
    private final String oldData;
    private final String email;
    private String newData;

    public boolean isValid(){
        if((newData == null || newData.isBlank())
                ||(oldData == null || oldData.isBlank())
                ||(email == null || email.isBlank())){
            return false;
        }
        if (newData.equals(oldData)){
            return false;
        }
        return true;
    }
}
