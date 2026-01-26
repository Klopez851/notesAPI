package com.example.notesAPI.service;

import com.example.notesAPI.dto.apiResponseDTO;
import com.example.notesAPI.dto.User.userInfoDTO;
import com.example.notesAPI.model.UITemplate;
import com.example.notesAPI.model.userTable;
import com.example.notesAPI.repository.uiTemplateRepository;
import com.example.notesAPI.repository.userRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class userService {

    private final userRepository userRepo;
    private final uiTemplateRepository uiTemplateRepo;

    public userService(userRepository userRepo, uiTemplateRepository uiTemplateRepo){
        this.userRepo = userRepo;
        this.uiTemplateRepo = uiTemplateRepo;
    }

    public apiResponseDTO createUser(userInfoDTO userDTO) {
        userTable user = new userTable();
        UITemplate template = uiTemplateRepo.getById(1);//default template for all users

        user.setUsername(userDTO.getUsername());
        user.setPasswordHash(userDTO.getPasswordHash());
        user.setEmail(userDTO.getEmail());
        user.setUiTemplate(template);
        user.setCreatedAt(LocalDateTime.now());

        userRepo.save(user);

        return new apiResponseDTO<>(true,"user created successfully", user.toString());
    }

}
