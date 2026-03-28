package com.example.notesAPI.service;

import com.example.notesAPI.dto.apiResponseDTO;
import com.example.notesAPI.dto.User.userInfoDTO;
import com.example.notesAPI.errorHandler.UserAlreadyExistsException;
import com.example.notesAPI.model.UserTable;
import com.example.notesAPI.repository.userRepository;
import lombok.AllArgsConstructor;
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


    public apiResponseDTO<String> createUser(userInfoDTO userDTO) {
        //clean the data
        String username = userDTO.getUsername().trim();
        String email = userDTO.getEmail().trim().toLowerCase(); //to have consistency in email format in db
        String password = userDTO.getUserPassword().trim();

        //validate username length
        if(username.length() > 50){
            throw new IllegalArgumentException("Username is too long, needs to be less than 51 characters");
        }

        //validate email length
        if(email.length() > 254){
            throw new IllegalArgumentException("Email is too long, needs to be less than 254 characters");
        }

        //Create user object
        UserTable user = new UserTable();

        user.setUsername(username);
        user.setUserPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setCreatedAt(LocalDateTime.now());

        //check if user exists
        if(userRepo.existsByEmail(user.getEmail())){
            throw new UserAlreadyExistsException("A user with that email already exists.");
        }

        //if it doesn't add the user to the db
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
