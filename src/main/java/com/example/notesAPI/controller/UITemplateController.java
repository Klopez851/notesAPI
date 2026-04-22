package com.example.notesAPI.controller;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.UITemplate.CreateTemplateDTO;
import com.example.notesAPI.dto.UITemplate.GetTemplateDTO;
import com.example.notesAPI.dto.UITemplate.UpdateTemplateDTO;
import com.example.notesAPI.service.UITemplateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

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

    ////////////////////
    /// GET METHODS ///
    ////////////////////

    @GetMapping("/getTemplates")
    public ApiResponseDTO<List<GetTemplateDTO>> getTemplates(@RequestBody HashMap<String, String> userEmail, HttpServletRequest request){
        if(userEmail.get("email") == null || userEmail.get("email").isBlank()){
            throw new IllegalArgumentException("please provide a valid email");
        }
        return service.getTemplates(userEmail,request);
    }

    ////////////////////
    /// PATCH METHODS ///
    ////////////////////

    @PatchMapping("/updateTemplateDetails")
    public ApiResponseDTO<String> updateTemplateDetails(@RequestBody UpdateTemplateDTO template, HttpServletRequest request){
        if(!template.isValid()){
            throw new IllegalArgumentException("All fields (email, newInfo) must be filled out");
        }

        return service.updateTemplateDetails(template, request);
    }

    @PatchMapping("/updateTemplateName")

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
