package com.example.notesAPI.dto.UITemplate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateTemplateDTO {
    @Schema(name = "email",example = "sampleemail@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(name = "templateName",example = "MyTemplate", requiredMode = Schema.RequiredMode.REQUIRED)
    private String templateName;

    @Schema(name = "templateDetails",example = "<TBD>", requiredMode = Schema.RequiredMode.REQUIRED)
    private String templateDetails;

    @JsonIgnore
    public boolean isValid(){
        if (email == null || email.isBlank()
        || templateName == null || templateName.isBlank()
        || templateDetails == null || templateDetails.isBlank()){

            return false;
        }

        return true;
    }


}
