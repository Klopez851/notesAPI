package com.example.notesAPI.dto.Note;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetNoteDTO {
    @Schema(name = "noteID",example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private int noteID;

    @JsonIgnore
    public boolean isValid(){
        if(email == null || email.isBlank() ||
        noteID<1){
            return false;
        }
        return true;
    }

}
