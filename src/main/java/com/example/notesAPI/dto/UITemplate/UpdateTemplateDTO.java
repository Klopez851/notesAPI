package com.example.notesAPI.dto.UITemplate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateTemplateDTO {
    private String email;
    private String templateID;
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
