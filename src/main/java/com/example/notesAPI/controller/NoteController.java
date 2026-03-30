package com.example.notesAPI.controller;

//import com.example.notesAPI.dto.createNoteDTO;
//import com.example.notesAPI.dto.updateNoteDTO;
import com.example.notesAPI.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/note")
@RequestMapping("/")
public class NoteController {
    private final NoteService service;

    @Autowired
    public NoteController(NoteService service) {
        this.service = service;
    }

//    @PostMapping("/create")
//    public apiResponseDTO createNote(@RequestBody createNoteDTO note ){
//
//    }


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
