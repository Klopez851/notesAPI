package com.example.notesAPI.controller;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.Label.LabelDTO;
import com.example.notesAPI.repository.LabelRepository;
import com.example.notesAPI.repository.UserRepository;
import com.example.notesAPI.service.LabelService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/label")
public class LabelController {

    private LabelRepository labelRepo;
    private UserRepository userRepo;
    private LabelService service;

    //////////////////////
    /// POST MAPPING/S ///
    /////////////////////
    @PostMapping("/createLabel")
    public ApiResponseDTO<String> createLabel(@RequestBody HashMap<String, String> userLabel){
        if((userLabel.get("email")==null || userLabel.get("email").isBlank())
                || (userLabel.get("label")==null||userLabel.get("label").isBlank())){
            throw new IllegalArgumentException("label name must be filled out and a valid email must be provided");
        }
        return service.createLabel(userLabel);
    }

    /////////////////////
    /// GET MAPPING/S ///
    /////////////////////

    @GetMapping("/getLabels")
    public ApiResponseDTO<List<LabelDTO>> getLabels(@RequestBody HashMap<String,String> userEmail){
        //validate input
        if(userEmail.get("email") == null || userEmail.get("email").isBlank()){
            throw new IllegalArgumentException("A valid email is needed to get labels");
        }

        return service.getLabels(userEmail);
    }

    //might create endpoint to get individual labels if needed

    ///////////////////////
    /// PATCH MAPPING/S ///
    ///////////////////////

    @PatchMapping("/updateLabel")
    public ApiResponseDTO<String> updateLabel(@RequestBody HashMap<String,String> reqLabel){
        if((reqLabel.get("labelID") == null || reqLabel.get("labelID").isBlank())
                || reqLabel.get("labelName") == null || reqLabel.get("labelName").isBlank()){
            throw new IllegalArgumentException("All fields must be filled");
        }

        return service.updateLabel(reqLabel);
    }

    ////////////////////////
    /// DELETE MAPPING/S ///
    ////////////////////////
    @DeleteMapping("/deleteLabel")
    public ApiResponseDTO<String> deleteLabel(@RequestBody HashMap<String, Integer> labelID){
        if(labelID.get("labelID")==null){
            throw new IllegalArgumentException("Please provide a valid label id");
        }

        return service.deleteLabel(labelID);
    }
}
