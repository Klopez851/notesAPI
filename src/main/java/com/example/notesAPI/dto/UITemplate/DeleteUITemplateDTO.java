package com.example.notesAPI.dto.UITemplate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeleteUITemplateDTO {
    @Schema(name = "templateID",example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String templateID;

    @Schema(name = "email",example = "sampleemail@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @JsonIgnore
    public boolean isValid(){
        if(templateID == null || templateID.isBlank()
                || email == null || email.isBlank()){
            return false;
        }
        return true;
    }

}
