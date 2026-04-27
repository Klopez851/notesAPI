package com.example.notesAPI.controller;

import com.example.notesAPI.repository.NoteColorRepository;
import com.example.notesAPI.repository.UserRepository;
import com.example.notesAPI.service.NoteColorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
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
    /////////////////////

    //create note color

    /////////////////////
    /// GET MAPPING/S ///
    ////////////////////

    //Get all stored colors

    ///////////////////////
    /// PATCH MAPPING/S ///
    //////////////////////

    //update color hex

    ////////////////////////
    /// DELETE MAPPING/S ///
    ////////////////////////

    //delete note color
}
