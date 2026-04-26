package com.example.notesAPI.dto.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserDTO {
    @Schema(name = "email",example = "sampleemail@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @JsonIgnore
    public boolean isValid(){
        if(email == null || email.isBlank()){
            return false;
        }
        return true;
    }

}
