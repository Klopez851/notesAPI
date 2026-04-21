package com.example.notesAPI.controller;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.UITemplate.CreateTemplateDTO;
import com.example.notesAPI.service.UITemplateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;

@RestController
@AllArgsConstructor
@RequestMapping("/uitemplate")
public class UITemplateController {

    private final UITemplateService service;

    ////////////////////
    /// POST METHODS ///
    ////////////////////

    @PostMapping("/create")
    public ApiResponseDTO<String> createTemplate(@RequestBody CreateTemplateDTO template, HttpServletRequest request){
        if(!template.isValid()){
            throw new IllegalArgumentException("All fields (user email, template name, and template details) must be filled out");
        }
        return service.createTemplate(template, request);
    }
    //////////////////////
    /// DELETE METHODS ///
    //////////////////////

    @DeleteMapping("/deleteUserTemplate")
    public ApiResponseDTO<String> deleteTemplate(@RequestBody HashMap<String, String> template, HttpServletRequest request){
        //make sure data is valid
        if(template.get("templateID") == null || template.get("templateID").isBlank()
               || template.get("email") == null || template.get("email").isBlank()){
            throw new IllegalArgumentException("All fields (templateID and email) must be filled out");
        }
        //give data to service
        return service.deleteTemplate(template, request);
    }

    // might make an endpoint to delete default templates

}
