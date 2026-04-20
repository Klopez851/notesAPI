package com.example.notesAPI.controller;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.UITemplate.CreateTemplateDTO;
import com.example.notesAPI.service.UITemplateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@AllArgsConstructor
@RequestMapping("/uitemplate")
public class UITemplateController {

    private final UITemplateService service;

    @PostMapping("/create")
    public ApiResponseDTO<String> createTemplate(@RequestBody CreateTemplateDTO template, HttpServletRequest request){
        if(!template.isValid()){
            throw new IllegalArgumentException("All fields (user email, template name, and template details) must be filled out");
        }
        return service.createTemplate(template, request);
    }

//    @PostMapping("/create")
//    public apiResponseDTO createTemplate(@RequestBody CreateTemplateDTO template){
//        return(service.createTemplate(template));
//    }
}
