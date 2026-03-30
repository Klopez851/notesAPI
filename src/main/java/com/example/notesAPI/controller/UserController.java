package com.example.notesAPI.controller;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.User.UpdateEmailDTO;
import com.example.notesAPI.dto.User.UpdateUserInfoDTO;
import com.example.notesAPI.dto.User.UserInfoDTO;
import com.example.notesAPI.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    /////////////////////
    /// POST MAPPINGS ///
    /////////////////////

    //Adds new user to database
    @PostMapping("/createUser")
    public ApiResponseDTO createUser(@RequestBody UserInfoDTO user) {
        if(!user.isValid()){
            throw new IllegalArgumentException("All fields must be filled out");
        }
        return(service.createUser(user));
    }

    @PostMapping("/login")
    public String login(@RequestBody UserInfoDTO user){
        if(!user.isValid()){
            throw new IllegalArgumentException("All fields must be filled out");
        }
        return service.verify(user);
    }

    //////////////////////
    /// PATCH MAPPINGS ///
    //////////////////////

    @PatchMapping("/updateUsername")
    public ApiResponseDTO<String> updateUsername(@RequestBody UpdateUserInfoDTO usernameDTO){
        if(!usernameDTO.isValid()){
            throw new IllegalArgumentException("Must provide a new username"); //assuming front-end will provide correct user email in request
        }
        return service.updateUsername(usernameDTO.getNewData(), usernameDTO.getEmail());
    }

    @PatchMapping("/updateEmail")
    public ApiResponseDTO<String> updateEmail(@RequestBody UpdateEmailDTO emailDTO){
        if(!emailDTO.isValid()){
            throw new IllegalArgumentException("Must provide a new email");
        }
        return service.updateEmail(emailDTO.getOldEmail(),emailDTO.getNewEmail());
    }

    @PatchMapping("/updatePassword")
    public ApiResponseDTO<String> updatePassword(@RequestBody UpdateUserInfoDTO passwordDTO){
        if(!passwordDTO.isValid()){
            throw new IllegalArgumentException("Must provide a new password");//assuming front-end will provide correct user email in request
        }
        return service.updatePassword(passwordDTO.getNewData(),passwordDTO.getEmail());
    }

}
