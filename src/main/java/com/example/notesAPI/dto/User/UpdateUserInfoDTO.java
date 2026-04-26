package com.example.notesAPI.dto.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateUserInfoDTO {

    @Schema(name = "oldData",requiredMode = Schema.RequiredMode.REQUIRED)
    private String oldData;

    @Schema(name = "email", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(name = "newData", requiredMode = Schema.RequiredMode.REQUIRED)
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
