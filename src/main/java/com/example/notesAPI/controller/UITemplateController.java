package com.example.notesAPI.controller;

import com.example.notesAPI.dto.UITemplate.CreateTemplateDTO;
import com.example.notesAPI.service.UITemplateService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/uitemplate")
public class UITemplateController {

    private final UITemplateService service;

    public UITemplateController(UITemplateService service){
        this.service = service;
    }
    @PostMapping("/create")
    public void createTemplate(@RequestBody CreateTemplateDTO template){
        System.out.println(template.getTemplateDetails());;
    }

//    @PostMapping("/create")
//    public apiResponseDTO createTemplate(@RequestBody CreateTemplateDTO template){
//        return(service.createTemplate(template));
//    }
}
