package com.example.notesAPI.dto;

import com.example.notesAPI.model.Note;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class noteDTO {
    /////////////////
    /// VARIABLES ///
    /////////////////
    private String title;
    private String content;
    private String label;
    private Date updatedAt;

    ///////////////////
    /// CONSTRUCTOR ///
    ///////////////////
    public noteDTO(){}

    ///////////////
    /// METHODS ///
    ///////////////

    public noteDTO toDTO (Note note){
        noteDTO dto = new noteDTO(
                note.getTitle(),
                note.getContent(),
                note.getLabel(),
                note.getUpdatedAt()
        );
        return dto;
    }
}
