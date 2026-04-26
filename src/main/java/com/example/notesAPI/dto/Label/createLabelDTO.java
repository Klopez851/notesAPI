package com.example.notesAPI.dto.Label;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class createLabelDTO {
    @Schema(name = "email",example = "sampleemail@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(name = "label",example = "sample label", requiredMode = Schema.RequiredMode.REQUIRED)
    private String label;

    @JsonIgnore
    public boolean isValid(){
        if(email==null || email.isBlank()
                || label==null||label.isBlank()){
            return false;
        }
        return true;
    }
}
