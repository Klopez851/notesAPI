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

    @Schema(name = "oldEmail",example = "sampleemail@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String oldEmail;

    @Schema(name = "newEmail",example = "testsampleemail.com", requiredMode = Schema.RequiredMode.REQUIRED)
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

