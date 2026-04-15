package com.example.notesAPI.controller;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.User.*;
import com.example.notesAPI.errorHandler.UserNotFoundException;
import com.example.notesAPI.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private UserService service;

    /////////////////////
    /// POST MAPPINGS ///
    /////////////////////

    @PostMapping("/createUser")
    public ApiResponseDTO createUser(@RequestBody UserInfoDTO user) {
        if(!user.isValid()){
            throw new IllegalArgumentException("All fields (username, email, and password) must be filled out");
        }
        return(service.createUser(user));
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginDTO user){
        if(!user.isValid()){
            throw new IllegalArgumentException("All fields must be filled out");
        }
        return service.verify(user);
    }

    ////////////////////
    /// GET MAPPINGS ///
    ////////////////////

    @GetMapping("/getUser")
    public ApiResponseDTO<UserInfoDTO> getUser(@RequestBody GetUserDTO user){
        if(!user.isValid()){
            throw  new IllegalArgumentException("All fields must be filled out");
        }
        return service.getUser(user.getEmail());
    }

    //////////////////////
    /// PATCH MAPPINGS ///
    //////////////////////

    @PatchMapping("/updateEmail")
    public ApiResponseDTO<String> updateEmail(@RequestBody UpdateEmailDTO emailDTO, HttpServletRequest request){
        if(!emailDTO.isValid()){
            throw new IllegalArgumentException("Must provide a new email");
        }
        return service.updateEmail(emailDTO, request);
    }

    @PatchMapping("/updateUsername")
    public ApiResponseDTO<String> updateUsername(@RequestBody UpdateUserInfoDTO usernameDTO, HttpServletRequest request){
        if(!usernameDTO.isValid()){
            throw new IllegalArgumentException("Must provide a new username"); //assuming front-end will provide correct user email in request
        }
        return service.updateUsername(usernameDTO, request);
    }

    @PatchMapping("/updatePassword")
    public ApiResponseDTO<String> updatePassword(@RequestBody UpdateUserInfoDTO passwordDTO, HttpServletRequest request){
        if(!passwordDTO.isValid()){
            throw new IllegalArgumentException("Must provide a new password");//assuming front-end will provide correct user email in request
        }
        return service.updatePassword(passwordDTO, request);
    }

    //////////////////////
    /// DELETE MAPPING ///
    //////////////////////

    @DeleteMapping("/deleteUser")
    public ApiResponseDTO<String> deleteUser(@RequestBody GetUserDTO user, HttpServletRequest request){
        if(!user.isValid()){
            throw new UserNotFoundException("please provide a valid email");
        }
        return service.deleteUser(user, request);
        //ensure user and jwt token info matches
    }

}
