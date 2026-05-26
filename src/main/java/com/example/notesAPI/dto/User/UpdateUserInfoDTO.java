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

    @Schema(name = "newData", requiredMode = Schema.RequiredMode.REQUIRED)
    private String newData;

    @JsonIgnore
    public boolean isValid(){
        if(newData == null || newData.isBlank()){
            return false;
        }
        return true;
    }
}
