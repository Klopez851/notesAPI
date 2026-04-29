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
    @Schema(name = "email",example = "sampleemail@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

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
        //The condition evaluates to true if email is null or blank, or Both title and content are null or blank.
        if((email == null || email.isBlank())
                || (title.isEmpty() && content.isEmpty())){
            return false;
        }
        return true;
    }

//    public boolean hasTitle(){
//        if(title == null || title.isBlank()){
//            return false;
//        }
//        return true;
//    }
//
//    public boolean hasContent(){
//        if(content == null || content.isBlank()){
//            return false;
//        }
//        return true;
//    }

    public boolean hasLabel(){
        if(labelID.isEmpty()){
            return false;
        }
        return true;
    }

    public boolean hasColor(){
        if(noteColorID.isEmpty()){
            return false;
        }
        return true;
    }

}
