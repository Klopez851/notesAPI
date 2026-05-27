package com.example.notesAPI.dto.Note;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateCosmeticDTO {
    @Schema(name = "noteID",example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private int noteID;

    @Schema(name = "cosmetics",example = "<TBD>", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cosmetics;

    @JsonIgnore
    public boolean isValid(){
        if(noteID<1
                || cosmetics == null || cosmetics.isBlank()){
            return false;
        }
        return true;
    }
}
