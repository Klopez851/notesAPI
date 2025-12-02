package com.example.notesAPI.dto;

import com.example.notesAPI.model.Note;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//Although this and createNoteDTO have the same code, the validation for each is different, therefore they have
//to be separate files
public class updateNoteDTO {
    /////////////////
    /// VARIABLES ///
    /////////////////
    private String title;
    private String content;
    private String label;

    ///////////////////
    /// CONSTRUCTOR ///
    ///////////////////
    public updateNoteDTO(){}

    public static updateNoteDTO toDTO(Note note){
        updateNoteDTO dto = new updateNoteDTO(
            note.getTitle(),
            note.getContent(),
            note.getLabel()
        );
        return dto;
    }
}
