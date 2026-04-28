package com.example.notesAPI.controller;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.EmailDTO;
import com.example.notesAPI.dto.noteColor.CreateNoteColorDTO;
import com.example.notesAPI.dto.noteColor.DeleteNoteColorDTO;
import com.example.notesAPI.dto.noteColor.NoteColorDTO;
import com.example.notesAPI.dto.noteColor.UpdateNoteColorDTO;
import com.example.notesAPI.service.NoteColorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Tag(name = "Note Color Endpoints")
@RestController
@RequestMapping(("/noteColor"))
public class NoteColorController {

    private final NoteColorService service;

    //////////////////////
    /// POST MAPPING/S ///
    //////////////////////

    @Operation(summary = "stores a color", description = "stores a custom color by hex number")
    @PostMapping("/createColor")
    public ApiResponseDTO<String> createNoteColor(@RequestBody CreateNoteColorDTO colorDTO, HttpServletRequest request){
        if(!colorDTO.isValid()){
            throw new IllegalArgumentException("All fields (email, colorHex) must be filled out");
        }

        return service.createNoteColor(colorDTO, request);
    }

    /////////////////////
    /// GET MAPPING/S ///
    /////////////////////

    @Operation(summary = "fetch colors", description = " fetch all colors associated with the provided emailDTO")
    @GetMapping("/getColors")
    public ApiResponseDTO<List<NoteColorDTO>> getNoteColors(@RequestBody EmailDTO emailDTO, HttpServletRequest request){
        if(!emailDTO.isValid()){
            throw new IllegalArgumentException("Please provide a valid email");
        }
        return service.getNoteColors(emailDTO, request);
    }

    ///////////////////////
    /// PATCH MAPPING/S ///
    ///////////////////////

    @Operation(summary = "updates an existing color", description = "updates an existing color")
    @PatchMapping("/updateColor")
    public ApiResponseDTO<String> updateNoteColor(@RequestBody UpdateNoteColorDTO colorDTO, HttpServletRequest request){
        if(!colorDTO.isValid()){
            throw new IllegalArgumentException("All fields (email, colorID, newColor) must be filled out");
        }
        return service.updateNoteColor(colorDTO, request);
    }

    ////////////////////////
    /// DELETE MAPPING/S ///
    ////////////////////////

    @Operation(summary = "Deletes a color", description = "deletes an existing custom color associated with the provided email")
    @DeleteMapping("/deleteColor")
    public ApiResponseDTO<String> deleteNoteColor(@RequestBody DeleteNoteColorDTO colorDTO, HttpServletRequest request){
        if(!colorDTO.isValid()){
            throw new IllegalArgumentException("All fields (email, colorID) must be filled out");
        }
        return service.deleteCoteColor(colorDTO, request);
    }
}
