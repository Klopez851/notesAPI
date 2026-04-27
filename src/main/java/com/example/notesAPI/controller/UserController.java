package com.example.notesAPI.controller;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.EmailDTO;
import com.example.notesAPI.dto.User.*;
import com.example.notesAPI.errorHandler.ResourceNotFoundException;
import com.example.notesAPI.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "User Endpoints")
@RequestMapping("/user")
public class UserController {

    private UserService service;

    /////////////////////
    /// POST MAPPINGS ///
    /////////////////////

    @Operation(summary = "Creates a new user", description = "Creates a new user if provided with a non-existent email")
    @PostMapping("/createUser")
    public ApiResponseDTO createUser(@RequestBody UserInfoDTO user) {
        if(!user.isValid()){
            throw new IllegalArgumentException("All fields (username, email, and password) must be filled out");
        }
        return(service.createUser(user));
    }

    @Operation(summary = "Allows user to login",description = "Allows user to log in and returns a custom JWT token")
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

    @Operation(summary = "fetches user information",description = "fetches user information given that a valid email is provided")
    @GetMapping("/getUser")
    public ApiResponseDTO<UserInfoDTO> getUser(@RequestBody EmailDTO user){
        if(!user.isValid()){
            throw  new IllegalArgumentException("All fields must be filled out");
        }
        return service.getUser(user.getEmail());
    }

    //////////////////////
    /// PATCH MAPPINGS ///
    //////////////////////

    @Operation(summary = "Allows user to update their email",description = "Allows user to update their email to a non-existing email")
    @PatchMapping("/updateEmail")
    public ApiResponseDTO<String> updateEmail(@RequestBody UpdateEmailDTO emailDTO, HttpServletRequest request){
        if(!emailDTO.isValid()){
            throw new IllegalArgumentException("Must provide new and old email");
        }
        return service.updateEmail(emailDTO, request);
    }

    @Operation(summary = "Allows user to update their username",description = "Allows user to update their username")
    @PatchMapping("/updateUsername")
    public ApiResponseDTO<String> updateUsername(@RequestBody UpdateUserInfoDTO usernameDTO, HttpServletRequest request){
        if(!usernameDTO.isValid()){
            throw new IllegalArgumentException("Must provide a new username"); //assuming front-end will provide correct user email in request
        }
        return service.updateUsername(usernameDTO, request);
    }

    @Operation(summary = "Allows user to update their password",description = "Allows user to update their password")
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
    @Operation(summary = "Allows user to delete their account",description = "Allows user to delete their account and everything related to them")
    @DeleteMapping("/deleteUser")
    public ApiResponseDTO<String> deleteUser(@RequestBody EmailDTO user, HttpServletRequest request){
        if(!user.isValid()){
            throw new ResourceNotFoundException("please provide a valid email");
        }
        return service.deleteUser(user, request);
        //ensure user and jwt token info matches
    }

}
