package com.example.notesAPI.service;

import com.example.notesAPI.dto.UITemplate.CreateTemplateDTO;
import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.model.UITemplate;
import com.example.notesAPI.repository.uiTemplateRepository;
import com.example.notesAPI.repository.userRepository;
import org.springframework.stereotype.Service;

@Service
public class UITemplateService {

    private final uiTemplateRepository templateRepo;
    private final userRepository userRepo;

    public UITemplateService(uiTemplateRepository templateRepo, userRepository userRepo){
        this.templateRepo = templateRepo;
        this.userRepo = userRepo;
    }

    //userid can be null, if null itlls be saved as a null userid
    public ApiResponseDTO createTemplate(CreateTemplateDTO templateDTO){
        UITemplate template = new UITemplate();
        if(templateDTO.getUserID() != null){
            template.setUser(userRepo.getReferenceById(templateDTO.getUserID()));
        }
        template.setTemplateName(templateDTO.getTemplateName());
        template.setTemplateDetails(templateDTO.getTemplateDetails());

        templateRepo.save(template);

        return new ApiResponseDTO<>(
                true,
                "Template created successfully",
                template.toString()
        );
    }
}

