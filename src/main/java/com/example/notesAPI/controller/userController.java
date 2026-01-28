package com.example.notesAPI.controller;

import com.example.notesAPI.dto.apiResponseDTO;
import com.example.notesAPI.dto.User.userInfoDTO;
import com.example.notesAPI.model.AuthRequest;
import com.example.notesAPI.service.JwtService;
import com.example.notesAPI.service.userService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class userController {

    private final userService service;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    //Adds new user to database
    @PostMapping("/createUser")
    public apiResponseDTO createUser(@RequestBody userInfoDTO user) {
        //VALIDATE INPUT
        return(service.createUser(user));
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest){
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword())
        );
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(authRequest.getUsername());
        }
        else{
            throw new UsernameNotFoundException("Invalid User Request!");
        }
    }

}
