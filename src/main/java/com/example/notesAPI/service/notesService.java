package com.example.notesAPI.service;

import com.example.notesAPI.dto.apiResponseDTO;
import com.example.notesAPI.dto.createNoteDTO;
import com.example.notesAPI.dto.noteResponseDTO;
import com.example.notesAPI.dto.updateNoteDTO;
import com.example.notesAPI.model.Note;
import com.example.notesAPI.repository.notesRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class notesService {

    private final notesRepository repo;
    public notesService(notesRepository repo){
        this.repo = repo;
    }

    public apiResponseDTO<createNoteDTO> createNote(String title, String content,String label){
        Note note = new Note(title,content,label);
        repo.save(note);
        return new apiResponseDTO<createNoteDTO>(true, "Note Created",createNoteDTO.toDTO(note));
    }

    public apiResponseDTO<noteResponseDTO> getNote(int id){
        Optional<Note> note = repo.findById(id);
        if(note.isPresent()){
            return new apiResponseDTO<noteResponseDTO>(
                    true,
                    "Note found",
                    noteResponseDTO.toDTO(note.get())
            );
        }
        else{
            return new apiResponseDTO<noteResponseDTO>(
                    false,
                    "Note not found",
                    null
            );
        }
    }

    public apiResponseDTO<updateNoteDTO> updateNote(int id,Optional<String> content, Optional<String> title,
                                                    Optional<String> label){
        //get old note values
        Note note = repo.getById(id);

        //update whatever needs to be updated;
        if(title.isPresent()){note.setTitle(String.valueOf(title));}
        if(content.isPresent()){note.setContent(String.valueOf(content));}
        if(label.isPresent()){note.setLabel(String.valueOf(label));}

        //save time of update
        note.setUpdatedAt(LocalDateTime.now());

        //save he updated note
        repo.save(note);

        //send api response
        return new apiResponseDTO<updateNoteDTO>(
                true,
                "Note successfully updated",
                updateNoteDTO.toDTO(note)
        );
    }

    public apiResponseDTO deleteNote(int id){
        if(repo.existsById(id)){
            repo.deleteById(id);
            return new apiResponseDTO(
                    true,
                    "Note successfully deleted",
                    null
            );
        }else{
            return new apiResponseDTO(
                    false,
                    "Note not found",
                    null
            );
        }
    }


}
