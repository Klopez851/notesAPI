package com.example.notesAPI.dto;

import com.example.notesAPI.model.Note;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Data // includes getters, setters, toString(), hashCode(), equals()
@AllArgsConstructor
public class noteResponseDTO {
    /////////////////
    /// VARIABLES ///
    /////////////////
    private int id;
    private String title;
    private String content;
    private String label;
    private LocalDateTime updatedAt;

    ///////////////////
    /// CONSTRUCTOR ///
    ///////////////////
    public noteResponseDTO(){}

    ///////////////
    /// METHODS ///
    ///////////////

    public static noteResponseDTO toDTO (Note note){
        noteResponseDTO dto = new noteResponseDTO(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getLabel(),
                note.getUpdatedAt()
        );
        return dto;
    }
}
