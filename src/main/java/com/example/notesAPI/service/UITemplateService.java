package com.example.notesAPI.service;

import com.example.notesAPI.dto.CreateTemplateDTO;
import com.example.notesAPI.dto.apiResponseDTO;
import com.example.notesAPI.model.UITemplate;
import com.example.notesAPI.model.UserTable;
import com.example.notesAPI.repository.uiTemplateRepository;
import com.example.notesAPI.repository.userRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UITemplateService {

    private final uiTemplateRepository templateRepo;
    private final userRepository userRepo;

    public UITemplateService(uiTemplateRepository templateRepo, userRepository userRepo){
        this.templateRepo = templateRepo;
        this.userRepo = userRepo;
    }

    //userid can be null, if null itlls be saved as a null userid
    public apiResponseDTO createTemplate(CreateTemplateDTO templateDTO){
        UITemplate template = new UITemplate();
        if(templateDTO.getUserID() != null){
            template.setUser(userRepo.getReferenceById(templateDTO.getUserID()));
        }
        template.setTemplateName(templateDTO.getTemplateName());
        template.setTemplateDetails(templateDTO.getTemplateDetails());

        templateRepo.save(template);

        return new apiResponseDTO<>(
                true,
                "Template created successfully",
                template.toString()
        );
    }
}

