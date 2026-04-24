package com.example.notesAPI.dto.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateUserInfoDTO {

    private String oldData;
    private String email;
    private String newData;

    @JsonIgnore
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
