package com.example.notesAPI.dto.Label;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateLabelDTO {

    @Schema(name = "labelID",example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String labelID;

    @Schema(name = "labelName",example = "interesting facts", requiredMode = Schema.RequiredMode.REQUIRED)
    private String labelName;

    @Schema(name = "email",example = "sampleemail@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @JsonIgnore
    public boolean isValid(){
        if ((labelID == null || labelID.isBlank())
                || (labelName == null || labelName.isBlank())
                || (email== null || email.isBlank())){
            return false;
        }
        return true;
    }

}
