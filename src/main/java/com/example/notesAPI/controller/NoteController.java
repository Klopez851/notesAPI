package com.example.notesAPI.controller;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.EmailDTO;
import com.example.notesAPI.dto.Label.UpdateBooleanStatusDTO;
import com.example.notesAPI.dto.Note.*;
import com.example.notesAPI.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Note Endpoints")
@AllArgsConstructor
@RequestMapping("/note")
public class NoteController {

    private final NoteService service;

    //////////////////////
    /// POST MAPPING/S ///
    //////////////////////

    @Operation(summary = "creates a note", description = "creates a note and associated it with the given email ")
    @PostMapping("/createNote")
    public ApiResponseDTO<String> createNote(@RequestBody CreateNoteDTO note, HttpServletRequest request) {
        if(!note.isValid()){
            throw new IllegalArgumentException("Note must have a title or text content and a valid email");
        }
        return service.createNote(note, request);
    }

    //////////////////////
    /// GET MAPPING/S ////
    //////////////////////

    @Operation(summary = "returns all user notes", description = "returns all the notes associated with the use email provided")
    @GetMapping("/getNotes")
    public ApiResponseDTO<List<NoteDTO>> getNotes(@RequestBody EmailDTO email, HttpServletRequest request){
        if(!email.isValid()){
            throw new IllegalArgumentException("please provide a valid email");
        }
        return service.getNotes(email, request);
    }

    @Operation(summary = "returns a single note", description = "Returns a single note with he provided email and note id")
    @GetMapping("/getNote")
    public ApiResponseDTO<NoteDTO> getNote(@RequestBody GetNoteDTO noteDTO, HttpServletRequest request){
        if(!noteDTO.isValid()){
            throw new IllegalArgumentException("All fields (email, noteID) must be filled out");
        }
        return service.getNote(noteDTO, request);
    }

    //////////////////////
    /// PUT MAPPING/S ////
    //////////////////////

//    @Operation(summary = "updates a note", description = "updates a users note")
//    @PutMapping("/updateNote")
//    public ApiResponseDTO<String> updateNote(@RequestBody UpdateNoteDTO noteDTO, HttpServletRequest request){
//        if(!noteDTO.isValid()){
//            throw new IllegalArgumentException ("- Either the title or body field must contain content (both cannot be empty).\n" +
//                    "- The fields pinned and hidden cannot both be set to true at the same time." +
//                    "- The id field must contain a value bigger than 0");
//        }
//
//        return service.updateNote(noteDTO, request);
//    }

    ////////////////////////
    /// PATCH MAPPING/S ////
    ////////////////////////

    @Operation(summary = "updates a notes pinned status")
    @PatchMapping("/updatePinned")
    public ApiResponseDTO<String> updatePinnedStatus (@RequestBody UpdateBooleanStatusDTO noteDTO, HttpServletRequest request){
        if(!noteDTO.isValid()){
            throw new IllegalArgumentException("All fields (email, noteID, newValue) must be filled out");
        }
        return service.updatePinned(noteDTO, request);
    }


    @Operation(summary = "updates a notes hidden status")
    @PatchMapping("/updateHidden")
    public ApiResponseDTO<String> updateHiddenStatus (@RequestBody UpdateBooleanStatusDTO noteDTO, HttpServletRequest request){
        if(!noteDTO.isValid()){
            throw new IllegalArgumentException("All fields (email, noteID, newValue) must be filled out");
        }
        return service.updateHidden(noteDTO, request);
    }


    @Operation(summary = "updates a notes deleted status")
    @PatchMapping("/updateDeleted")
    public ApiResponseDTO<String> updateDeletedStatus (@RequestBody UpdateBooleanStatusDTO noteDTO, HttpServletRequest request){
        if(!noteDTO.isValid()){
            throw new IllegalArgumentException("All fields (email, noteID, newValue) must be filled out");
        }
        return service.updateDeleted(noteDTO, request);
    }

    @Operation(summary = "updates a notes view only status")
    @PatchMapping("/updateViewOnly")
    public ApiResponseDTO<String> updateViewOnlyStatus (@RequestBody UpdateBooleanStatusDTO noteDTO, HttpServletRequest request){
        if(!noteDTO.isValid()){
            throw new IllegalArgumentException("All fields (email, noteID, newValue) must be filled out");
        }
        return service.updateViewOnly(noteDTO, request);
    }

    @Operation(summary = "updates a note's label")
    @PatchMapping("/updateLabel")
    public ApiResponseDTO<String> updateLabel (@RequestBody UpdateNoteLabelDTO labelDTO, HttpServletRequest request){
        if(!labelDTO.isValid()){
            throw new IllegalArgumentException("All fields (email, noteID, labelID) must be filled out");
        }
        return service.updateLabel(labelDTO, request);
    }

    @Operation(summary = "updates a note's color")
    @PatchMapping("/updateNoteColor")
    public ApiResponseDTO<String> updateNoteColor (@RequestBody UpdateColorDTO colorDTO, HttpServletRequest request){
        if(!colorDTO.isValid()){
            throw new IllegalArgumentException("All fields (email, noteID, colorID) must be filled out");
        }
        return service.updateNoteColor(colorDTO, request);
    }

    @Operation(summary = "updates a note's cosmetics")
    @PatchMapping("/updateCosmetics")
    public ApiResponseDTO<String> updateCosmetics(@RequestBody UpdateCosmeticDTO cosmeticsDTO, HttpServletRequest request){
        if(!cosmeticsDTO.isValid()){
            throw new IllegalArgumentException("All fields (email, noteID, cosmetics) must be filled out");
        }

        return service.updateCosmetics(cosmeticsDTO, request);
    }

    /////////////////////////
    /// DELETE MAPPING/S ////
    /////////////////////////

}

