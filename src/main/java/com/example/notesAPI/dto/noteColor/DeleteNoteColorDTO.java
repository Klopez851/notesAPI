package com.example.notesAPI.dto.noteColor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class DeleteNoteColorDTO {
    @Schema(name = "email",example = "sampleemail@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(name = "colorID",example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String colorID;

    @JsonIgnore
    public boolean isValid(){
        if(email == null || email.isBlank()
                || colorID == null || colorID.isBlank()){
            return false;
        }

        return true;
    }
}
