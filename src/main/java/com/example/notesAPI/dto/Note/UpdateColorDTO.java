package com.example.notesAPI.dto.Note;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateColorDTO {
    @Schema(name = "noteID",example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private int noteID;

    @Schema(name = "colorID",example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private int colorID;

    @JsonIgnore
    public boolean isValid(){
        if(noteID<1 ||colorID<1){
            return false;
        }

        return true;
    }
}
