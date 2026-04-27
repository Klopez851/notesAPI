package com.example.notesAPI.controller;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.EmailDTO;
import com.example.notesAPI.dto.UITemplate.CreateTemplateDTO;
import com.example.notesAPI.dto.UITemplate.DeleteUITemplateDTO;
import com.example.notesAPI.dto.UITemplate.GetTemplateDTO;
import com.example.notesAPI.dto.UITemplate.UpdateTemplateDTO;
import com.example.notesAPI.service.UITemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name="UI Template Endpoints")
@RequestMapping("/uitemplate")
public class UITemplateController {

    private final UITemplateService service;

    ////////////////////
    /// POST METHODS ///
    ////////////////////

    @Operation(summary = "creates ui templates", description = "allows users to store their custom ui templates")
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

    @Operation(summary = "fetches ui templates", description = "fetches all ui templates associated with the provided email")
    @GetMapping("/getTemplates")
    public ApiResponseDTO<List<GetTemplateDTO>> getTemplates(@RequestBody EmailDTO userEmail, HttpServletRequest request){
        if(!userEmail.isValid()){
            throw new IllegalArgumentException("please provide a valid email");
        }
        return service.getTemplates(userEmail,request);
    }

    ////////////////////
    /// PATCH METHODS ///
    ////////////////////

    @Operation(summary = "updates a template's details",description = "updates a ui template's details")
    @PatchMapping("/updateTemplateDetails")
    public ApiResponseDTO<String> updateTemplateDetails(@RequestBody UpdateTemplateDTO template, HttpServletRequest request){
        if(!template.isValid()){
            throw new IllegalArgumentException("All fields (email, newInfo) must be filled out");
        }

        return service.updateTemplateDetails(template, request);
    }

//    @PatchMapping("/updateTemplateName")

    //////////////////////
    /// DELETE METHODS ///
    //////////////////////

    @Operation(summary = "deletes a ui template",description = "deletes a given ui template as long as its associated with the given email")
    @DeleteMapping("/deleteUserTemplate")
    public ApiResponseDTO<String> deleteTemplate(@RequestBody DeleteUITemplateDTO template, HttpServletRequest request){
        //make sure data is valid
        if(!template.isValid()){
            throw new IllegalArgumentException("All fields (templateID and email) must be filled out");
        }
        //give data to service
        return service.deleteTemplate(template, request);
    }

    // might make an endpoint to delete default templates

}
