package com.example.notesAPI.dto.Note;

import com.example.notesAPI.model.Label;
import com.example.notesAPI.model.NoteColor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateNoteDTO {

    private String title;
    private String content;
    private String email;
    private Label label;
    private NoteColor noteColor;

    public boolean isValid(){
        //The condition evaluates to true if email is null or blank, or Both title and content are null or blank.
        if((email == null || email.isBlank())
                ||(title == null || title.isBlank())
                &&(content == null || content.isBlank())){
            return false;
        }
        return true;
    }

    public boolean hasTitle(){
        if(title == null || title.isBlank()){
            return false;
        }
        return true;
    }

    public boolean hasContent(){
        if(content == null || content.isBlank()){
            return false;
        }
        return true;
    }

    public boolean hasLabel(){
        if(label == null ){
            return false;
        }
        return true;
    }

    public boolean hasColor(){
        if(noteColor == null){
            return false;
        }
        return true;
    }



//    public static createNoteDTO toDTO(Note note){
//        createNoteDTO dto = new createNoteDTO(
//
//            note.getTitle(),
//            note.getContent(),
//            note.getLabel()
//        );
//        return dto;
//    }
}
