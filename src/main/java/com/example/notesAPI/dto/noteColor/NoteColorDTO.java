package com.example.notesAPI.dto.noteColor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteColorDTO {
    @Schema(name = "colorID",example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private int colorID;

    @Schema(name = "colorHex",example = "#b5a2c8", requiredMode = Schema.RequiredMode.REQUIRED)
    private String colorHEX;

    //this constructor matches the exact order the query NoteColorRepository.findAllByUser() requires
    // changes made here need to be reflected there.
    public NoteColorDTO(int colorID, String colorHex){
        this.colorID = colorID;
        this.colorHEX = colorHex;
    }
}
