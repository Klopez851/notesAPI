package com.example.notesAPI.dto.Label;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class LabelDTO {
    private int labelID;
    private String labelName;

    //this constructor matches the exact order the query LabelRepository.findAllByUser() requires
    // changes made here need to be reflected there.
    public LabelDTO(String labelName, int labelID){
        this.labelID =labelID;
        this.labelName=labelName;
    }

}
