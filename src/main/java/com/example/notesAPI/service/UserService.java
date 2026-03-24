package com.example.notesAPI.service;

import com.example.notesAPI.dto.apiResponseDTO;
import com.example.notesAPI.dto.User.userInfoDTO;
import com.example.notesAPI.model.UserTable;
import com.example.notesAPI.repository.uiTemplateRepository;
import com.example.notesAPI.repository.userRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserService {

    //using constructor injection with lombok annotations
    private userRepository userRepo;
    private BCryptPasswordEncoder passwordEncoder;
    private AuthenticationManager  authManager;
    private JWTService jwtService;


    public apiResponseDTO createUser(userInfoDTO userDTO) {
        //check if user exist
        UserTable user = new UserTable();

        user.setUsername(userDTO.getUsername());
        user.setUserPassword(passwordEncoder.encode(userDTO.getUserPassword()));
        user.setEmail(userDTO.getEmail());
        user.setCreatedAt(LocalDateTime.now());

        userRepo.save(user);

        return new apiResponseDTO<>(true,"user created successfully", user.toString());
    }

    public String verify(userInfoDTO user) {
        Authentication authenticate =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getUserPassword()));
                //auth manager returns an Authentication object and takes type authentication token
        if(authenticate.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }
        return "user not found";
    }
}
