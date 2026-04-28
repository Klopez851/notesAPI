package com.example.notesAPI.controller;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.noteColor.CreateNoteColorDTO;
import com.example.notesAPI.repository.NoteColorRepository;
import com.example.notesAPI.repository.UserRepository;
import com.example.notesAPI.service.NoteColorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@Tag(name = "Note Color Endpoints")
@RestController
@RequestMapping(("/noteColor"))
public class NoteColorController {

    private final NoteColorService service;

    //////////////////////
    /// POST MAPPING/S ///
    //////////////////////

    //create note color
    @Operation(summary = "stores a color", description = "stores a custom color by hex number")
    @PostMapping("/createNoteColor")
    public ApiResponseDTO<String> createNoteColor(@RequestBody CreateNoteColorDTO colorDTO, HttpServletRequest request){
        if(!colorDTO.isValid()){
            throw new IllegalArgumentException("All fields (email, colorHex) must be filled out");
        }

        return service.createNoteColor(colorDTO, request);
    }

    /////////////////////
    /// GET MAPPING/S ///
    /////////////////////

    //Get all stored colors

    ///////////////////////
    /// PATCH MAPPING/S ///
    ///////////////////////

    //update color hex

    ////////////////////////
    /// DELETE MAPPING/S ///
    ////////////////////////

    //delete note color
}
