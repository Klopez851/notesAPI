package com.example.notesAPI.service;

import com.example.notesAPI.dto.apiResponseDTO;
import com.example.notesAPI.model.UITemplate;
import com.example.notesAPI.repository.uiTemplateRepository;
import org.springframework.stereotype.Service;

@Service
public class UITemplateService {

    private final uiTemplateRepository templateRepo;

    public UITemplateService(uiTemplateRepository templateRepo){
        this.templateRepo = templateRepo;
    }

    public apiResponseDTO createTemplate(UITemplate template){
        templateRepo.save(template);

        return new apiResponseDTO<>(
                true,
                "Template created successfully",
                template.toString()
        );
    }
}

