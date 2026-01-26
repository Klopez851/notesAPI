package com.example.notesAPI.controller;

import com.example.notesAPI.dto.User.getUserDTO;
import com.example.notesAPI.dto.apiResponseDTO;
import com.example.notesAPI.dto.User.userInfoDTO;
import com.example.notesAPI.service.userService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class userController {

    private final userService service;

    public userController(userService userService){
        this.service = userService;
    }

    //Adds new user to database
    @PostMapping("/createUser")
    public apiResponseDTO createUser(@RequestBody userInfoDTO user) {
        //VALIDATE INPUT
        return(service.createUser(user));
    }

}
