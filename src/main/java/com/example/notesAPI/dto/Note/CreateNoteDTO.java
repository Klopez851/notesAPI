package com.example.notesAPI.dto.Note;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class CreateNoteDTO {

    @Schema(name = "title",example = "sample note title", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Optional<String> title;

    @Schema(name = "content",example = "<TBD>", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Optional<String> content;

    @Schema(name = "labelID",example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Optional<Integer> labelID;

    @Schema(name = "noteColorID",example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Optional<Integer> noteColorID;

    @JsonIgnore
    public boolean isValid(){
        if(title.isEmpty() && content.isEmpty()){
            return false;
        }
        return true;
    }
}
