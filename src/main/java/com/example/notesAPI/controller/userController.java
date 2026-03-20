package com.example.notesAPI.controller;

import com.example.notesAPI.dto.apiResponseDTO;
import com.example.notesAPI.dto.User.userInfoDTO;
import com.example.notesAPI.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        //VALIDATE INPUT
        return(service.createUser(user));
    }

    @PostMapping("/login")
    public String login(@RequestBody userInfoDTO user){
        return service.verify(user);
    }

}
