package com.example.notesAPI.dto.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserLoginDTO {
    @Schema(name = "email", example = "sampleemail@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(name = "userPassword", example = "UserPassword@123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userPassword;

    @JsonIgnore
    public boolean isValid() {
        if ((email == null || email.isBlank()) ||
                (userPassword == null || userPassword.isBlank())) {
            return false;
        }
        return true;
    }
}
