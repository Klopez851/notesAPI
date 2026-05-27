package com.example.notesAPI.dto.Note;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRawValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateNoteLabelDTO {
    @Schema(name = "noteID",example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private int noteID;

    @Schema(name = "labelID",example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private int labelID;

    @JsonIgnore
    public boolean isValid(){
        if(email == null || email.isBlank() || noteID<1 || labelID<1){
            return false;
        }

        return true;
    }

}
