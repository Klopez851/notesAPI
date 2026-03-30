package com.example.notesAPI.dto.UITemplate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTemplateDTO {
    private Integer userID; //using big int make it so that the value can be nullable
    private String templateName;
    private String templateDetails;

    public CreateTemplateDTO(){
    }
}
