package com.example.notesAPI.controller;

//import com.example.notesAPI.dto.createNoteDTO;
//import com.example.notesAPI.dto.updateNoteDTO;
import com.example.notesAPI.dto.apiResponseDTO;
import com.example.notesAPI.service.noteService;
        import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/note")
public class noteController {
    private final noteService service;

    public noteController(noteService service) {
        this.service = service;
    }

//    @PostMapping("/create")
//    public apiResponseDTO createNote(@RequestBody createNoteDTO note ){
//
//    }

}
//
//    @GetMapping("/{id}")
//    public apiResponseDTO getNote(@PathVariable int id){
//        return service.getNote(id);
//    }
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
//}
//
