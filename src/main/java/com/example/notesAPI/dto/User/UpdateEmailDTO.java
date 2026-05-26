package com.example.notesAPI.dto.User;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateEmailDTO {

    @Schema(name = "newEmail",example = "testsampleemail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String newEmail;

    @JsonIgnore
    public boolean isValid(){
        if((newEmail == null || newEmail.isBlank())) {
            return false;
        }
        return true;
    }
}

