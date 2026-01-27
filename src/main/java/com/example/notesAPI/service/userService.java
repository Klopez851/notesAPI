package com.example.notesAPI.service;

import com.example.notesAPI.dto.apiResponseDTO;
import com.example.notesAPI.dto.User.userInfoDTO;
import com.example.notesAPI.model.UserTable;
import com.example.notesAPI.repository.uiTemplateRepository;
import com.example.notesAPI.repository.userRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class userService {

    private final userRepository userRepo;
    private final uiTemplateRepository uiTemplateRepo;

    public userService(userRepository userRepo, uiTemplateRepository uiTemplateRepo){
        this.userRepo = userRepo;
        this.uiTemplateRepo = uiTemplateRepo;
    }

    public apiResponseDTO createUser(userInfoDTO userDTO) {
        UserTable user = new UserTable();

        user.setUsername(userDTO.getUsername());
        user.setPasswordHash(userDTO.getPasswordHash());
        user.setEmail(userDTO.getEmail());
        user.setCreatedAt(LocalDateTime.now());

        userRepo.save(user);

        return new apiResponseDTO<>(true,"user created successfully", user.toString());
    }

}
