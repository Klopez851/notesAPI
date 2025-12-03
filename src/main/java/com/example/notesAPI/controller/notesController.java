package com.example.notesAPI.controller;

import com.example.notesAPI.dto.apiResponseDTO;
import com.example.notesAPI.dto.createNoteDTO;
import com.example.notesAPI.dto.updateNoteDTO;
import com.example.notesAPI.service.notesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/note")
public class notesController {
    private final notesService service;

    public notesController(notesService service){
        this.service = service;
    }

    @PostMapping
    public apiResponseDTO createNote(@RequestBody createNoteDTO dto){
        //validate response
        try {
            if (dto.getTitle().isBlank() && dto.getContent().isBlank()) {
                return new apiResponseDTO<>(false, "Note must have a title or body.", null);
            }
            return service.createNote(Optional.ofNullable(dto.getTitle()), Optional.ofNullable(dto.getContent()),
                    Optional.ofNullable(dto.getLabel())
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public apiResponseDTO getNote(@PathVariable int id){
        return service.getNote(id);
    }

    @PutMapping("/{id}")
    public apiResponseDTO updateNote(@PathVariable int id, @RequestBody updateNoteDTO dto){
        return service.updateNote(id, Optional.ofNullable(dto.getContent()), Optional.ofNullable(dto.getTitle()),
                Optional.ofNullable(dto.getLabel()));
    }

    @DeleteMapping("/{id}")
    public apiResponseDTO deleteNote(@PathVariable int id){
        return service.deleteNote(id);
    }

}

