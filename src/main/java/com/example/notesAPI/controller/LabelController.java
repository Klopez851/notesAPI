package com.example.notesAPI.controller;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.model.Label;
import com.example.notesAPI.model.UserTable;
import com.example.notesAPI.repository.LabelRepository;
import com.example.notesAPI.repository.UserRepository;
import com.example.notesAPI.service.LabelService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@AllArgsConstructor
@RestController
@RequestMapping("/label")
public class LabelController {

    private LabelRepository labelRepo;
    private UserRepository userRepo;
    private LabelService service;

    @PostMapping("/createLabel")
    public ApiResponseDTO<String> createLabel(@RequestBody HashMap<String, String> userLabel){
        if((userLabel.get("email")==null || userLabel.get("email").isBlank())
                || (userLabel.get("label")==null||userLabel.get("label").isBlank())){
            throw new IllegalArgumentException("label name must be filled out and a valid email must be provided");
        }
        return service.createLabel(userLabel);
    }
}
