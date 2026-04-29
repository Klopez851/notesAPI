package com.example.notesAPI.dto.Note;

import com.example.notesAPI.dto.Label.LabelDTO;
import com.example.notesAPI.dto.noteColor.NoteColorDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class NoteDTO {

    private int id;

    private Optional<LabelDTO> label;
    private Optional<NoteColorDTO> noteColor;
    private Optional<String> title;
    private Optional<String> textContent;
    private Optional<String> cosmetics;

    private boolean pinned;
    private boolean hidden;
    private boolean viewOnly = false; //will remove default value once i figure out how to share notes amongst users
    private boolean deleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime timeLeftBeforeDeletion;

    @JsonIgnore
    public boolean isValid(){
        if(isEqual(pinned, hidden)){
            return false;
        }

        if ((getTitle().isEmpty() || title.get().isBlank())
                && getTextContent().isEmpty() || textContent.get().isBlank()){
            return false;
        }

        return true;
    }

    private boolean isEqual(boolean attribute1, boolean attribute2){
        return (attribute1 == attribute2);
    }
}
