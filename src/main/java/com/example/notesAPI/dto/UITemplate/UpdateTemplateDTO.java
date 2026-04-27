package com.example.notesAPI.dto.UITemplate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateTemplateDTO {
    @Schema(name = "email",example = "sampleemail@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(name = "templateID",example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String templateID;

    @Schema(name = "newInfo",example = "<TBD>", requiredMode = Schema.RequiredMode.REQUIRED)
    private String newInfo;

    @JsonIgnore
    public boolean isValid(){
        if((email == null || email.isBlank())
                || (newInfo == null || newInfo.isBlank())
                || (templateID == null || templateID.isBlank())){
            return false;
        }
        return true;
    }
}
