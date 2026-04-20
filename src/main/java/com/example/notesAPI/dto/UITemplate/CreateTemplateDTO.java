package com.example.notesAPI.dto.UITemplate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateTemplateDTO {
    private String email;
    private String templateName;
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
