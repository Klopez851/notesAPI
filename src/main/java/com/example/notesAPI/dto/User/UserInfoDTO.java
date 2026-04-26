package com.example.notesAPI.dto.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoDTO {

    @Schema(name = "username",example = "SampleUser123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(name = "email",example = "sampleemail@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(name = "userPassword",example = "UserPassword@123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userPassword;

    @JsonIgnore //jackson will automatically make add an unwanted "valid" field to the json bc of this method w/o the annotation
    public boolean isValid(){
        if((username == null || username.isBlank()) ||
                (email == null || email.isBlank()) ||
                (userPassword == null || userPassword.isBlank())) {
            return false;
        }
        return true;
    }
}
