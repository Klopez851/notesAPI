package com.example.notesAPI.dto.UITemplate;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonPropertyOrder({"templateID","templateName","templateDetails"})
public class GetTemplateDTO {
    private String templateName, templateDetails;
    private int templateID;

    //this constructor matches the exact order the query UITemplateRepository.getAllNoteByUser() requires
    // changes made here need to be reflected there.
    public GetTemplateDTO(int templateID, String templateName, String templateDetails) {
        this.templateID = templateID;
        this.templateName = templateName;
        this.templateDetails = templateDetails;
    }
}
