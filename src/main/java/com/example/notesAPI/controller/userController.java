package com.example.notesAPI.controller;

import com.example.notesAPI.dto.apiResponseDTO;
import com.example.notesAPI.dto.User.userInfoDTO;
import com.example.notesAPI.service.UserService;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.SpringVersion;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class userController {
    @Autowired
    private UserService service;

    //Adds new user to database
    @PostMapping("/createUser")
    public apiResponseDTO createUser(@RequestBody userInfoDTO user) {
        if(!isUserInfoValid(user)){
            throw new IllegalArgumentException("All fields must be filled out");
        }
        return(service.createUser(user));
    }

    @PostMapping("/login")
    public String login(@RequestBody userInfoDTO user){
        return service.verify(user);
    }


    ///////////////////////
    /// PRIVATE METHODS ///
    ///////////////////////
    private boolean isUserInfoValid(userInfoDTO user){
        if((user.getUsername() == null || user.getUsername().isBlank()) ||
                (user.getEmail() == null || user.getEmail().isBlank()) ||
                (user.getUserPassword() == null || user.getUserPassword().isBlank())) {
            return false;
        }

        return true;
    }

}
