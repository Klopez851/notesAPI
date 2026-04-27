package com.example.notesAPI.controller;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.Label.DeleteLabelDTO;
import com.example.notesAPI.dto.Label.LabelDTO;
import com.example.notesAPI.dto.Label.CreateLabelDTO;
import com.example.notesAPI.dto.Label.UpdateLabelDTO;
import com.example.notesAPI.dto.EmailDTO;
import com.example.notesAPI.repository.LabelRepository;
import com.example.notesAPI.repository.UserRepository;
import com.example.notesAPI.service.LabelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.*;

import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "Label Endpoints")
@RequestMapping("/label")
public class LabelController {

    private LabelService service;

    //////////////////////
    /// POST MAPPING/S ///
    /////////////////////

    @Operation(summary = "Creates a label", description = "Allows user to create a label")
    @PostMapping("/createLabel")
    public ApiResponseDTO<String> createLabel(@RequestBody CreateLabelDTO userLabel, HttpServletRequest request){
        if(!userLabel.isValid()){
            throw new IllegalArgumentException("label name must be filled out and a valid email must be provided");
        }
        return service.createLabel(userLabel, request);
    }

    /////////////////////
    /// GET MAPPING/S ///
    /////////////////////

    @Operation(summary = "fetches labels", description = "fetches all labels associated with the provided email")
    @GetMapping("/getLabels")
    public ApiResponseDTO<List<LabelDTO>> getLabels(@RequestBody EmailDTO userEmail, HttpServletRequest request){
        //validate input
        if(!userEmail.isValid()){
            throw new IllegalArgumentException("A valid email is needed to get labels");
        }

        return service.getLabels(userEmail, request);
    }

    //might create endpoint to get individual labels if needed

    ///////////////////////
    /// PATCH MAPPING/S ///
    ///////////////////////
    @Operation(summary = "updates a label",description = "allows users to update any of the labels associated with the, as long as a different label name from the name stored is provided")
    @PatchMapping("/updateLabel")
    public ApiResponseDTO<String> updateLabel(@RequestBody UpdateLabelDTO reqLabel, HttpServletRequest request){
        if(!reqLabel.isValid()){
            throw new IllegalArgumentException("All fields(labelID, labelName, email) must be filled");
        }

        return service.updateLabel(reqLabel, request);
    }

    ////////////////////////
    /// DELETE MAPPING/S ///
    ////////////////////////

    @Operation(summary = "deletes a label", description = "Allows users to delete any of their labels as long as they exists in the db")
    @DeleteMapping("/deleteLabel")
    public ApiResponseDTO<String> deleteLabel(@RequestBody DeleteLabelDTO label, HttpServletRequest request){
        if(!label.isValid()){
            throw new IllegalArgumentException("All fields (labelID, email) must be filled out");
        }

        return service.deleteLabel(label, request);
    }
}
