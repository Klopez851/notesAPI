package com.example.notesAPI.controller;

//import com.example.notesAPI.dto.createNoteDTO;
//import com.example.notesAPI.dto.updateNoteDTO;
import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.Note.CreateNoteDTO;
import com.example.notesAPI.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/note")
public class NoteController {
    private final NoteService service;

    @PostMapping("/createNote")
    public ApiResponseDTO<String> createNote(@RequestBody CreateNoteDTO note ){
        if(!note.isValid()){
            throw new IllegalArgumentException("Note must have a title or text content");
        }
        return service.createNote(note);
    }


//
    @GetMapping()
    public String getNote(){
        return "hello";
    }
//
//    @PutMapping("/{id}")
//    public apiResponseDTO updateNote(@PathVariable int id, @RequestBody updateNoteDTO dto){
//        return service.updateNote(id, Optional.ofNullable(dto.getContent()), Optional.ofNullable(dto.getTitle()),
//                Optional.ofNullable(dto.getLabel()));
//    }
//
//    @DeleteMapping("/{id}")
//    public apiResponseDTO deleteNote(@PathVariable int id){
//        return service.deleteNote(id);
//    }
//
}
//
