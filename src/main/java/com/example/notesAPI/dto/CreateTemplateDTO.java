package com.example.notesAPI.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTemplateDTO {
    private String templateName;
    private String templateDetails;

    public CreateTemplateDTO(String name, String templateDetails){
        this.templateName=name;
        this.templateDetails=templateDetails;
    }
}
