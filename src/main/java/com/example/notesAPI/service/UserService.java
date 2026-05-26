package com.example.notesAPI.service;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.User.*;
import com.example.notesAPI.errorHandler.DatabaseErrorException;
import com.example.notesAPI.errorHandler.ResourceAlreadyExistsException;
import com.example.notesAPI.errorHandler.ResourceNotFoundException;
import com.example.notesAPI.model.UserTable;
import com.example.notesAPI.repository.UserRepository;
import com.example.notesAPI.utilClasses.RequestValidationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    // CONSTANTS //
    private final int MAX_USERNAME_LENGTH = 50;
    private final int MAX_EMAIL_LENGTH = 254;

    //using constructor injection with lombok annotations
    private UserRepository userRepo;
    private BCryptPasswordEncoder passwordEncoder;
    private AuthenticationManager authManager;
    private JWTService jwtService;
    private RequestValidationService requestUtil;

    /// /////////////////
    /// POST METHODS ///
    /// /////////////////

    public String verify(UserLoginDTO user) {
        Authentication authenticate =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getUserPassword()));
        //auth manager returns an Authentication object and takes type authentication token
        if (authenticate.isAuthenticated()) {
            return "token: " + jwtService.generateToken(user.getEmail().toLowerCase());
        } else {
            throw new ResourceNotFoundException("User not found with the email " + user.getEmail());
        }
    }

    public ApiResponseDTO<String> createUser(UserInfoDTO userDTO) {
        //clean the data
        String username = userDTO.getUsername().strip();
        String email = userDTO.getEmail().strip().toLowerCase(); //to have consistency in email format in db
        String password = userDTO.getUserPassword().strip();

        //validate username length
        if (username.length() > MAX_USERNAME_LENGTH) {
            throw new IllegalArgumentException("Username is too long, needs to be less than 51 characters");
        }

        //validate email length
        if (email.length() > MAX_EMAIL_LENGTH) {
            throw new IllegalArgumentException("Email is too long, needs to be less than 254 characters");
        }

        //Create user object
        UserTable user = new UserTable();

        user.setUsername(username);
        user.setUserPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setCreatedAt(LocalDateTime.now());

        //check if user exists
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new ResourceAlreadyExistsException("A user with that email already exists.");
        }

        //if it doesn't add the user to the db
        try {
            userRepo.save(user);
        } catch (Exception e) {
            throw new DatabaseErrorException(e.getMessage());
        }

        return new ApiResponseDTO<>(true, "user created successfully", null);
    }

    /// /////////////////
    /// GET METHODS ///
    /// /////////////////

    public ApiResponseDTO<UserInfoDTO> getUser(HttpServletRequest request) {
        //clean the data
        String email = requestUtil.extractEmailClaim(request).toLowerCase();
        UserInfoDTO userInfo;

        //get the user from the db
        Optional<UserTable> user = userRepo.findByEmail(email);

        //make sure the user exists
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User with the email " + email + " not found");
        } else {
            userInfo = new UserInfoDTO(user.get().getUsername(), user.get().getEmail(), null);
        }
        //send back the user dto
        return new ApiResponseDTO<UserInfoDTO>(true, "User Found", userInfo);
    }

    /// //////////////////
    /// PATCH METHODS ///
    /// //////////////////

    public ApiResponseDTO<String> updateUsername(UpdateUserInfoDTO userInfoDTO, HttpServletRequest request) {
        //clean the data
        String newUsername = userInfoDTO.getNewData().strip();
        String userEmail = requestUtil.extractEmailClaim(request);

        //validate the input some more
        if (newUsername.length() > MAX_USERNAME_LENGTH) {
            throw new IllegalArgumentException("Username is too long");
        }

        //get the user from the db
        Optional<UserTable> user = userRepo.findByEmail(userEmail);

        //update the user info
        if (user.isEmpty()) {
            throw new ResourceNotFoundException(userEmail + " is not an existing user account. " +
                    "Username updates require a valid email to identify the user to update.");
        }

        if(user.get().getUsername().equals(newUsername)){
            throw new ResourceAlreadyExistsException("No changes made. The new username matches the current username.");
        }else{
            user.get().setUsername(newUsername);
        }

        //save the user
        try {
            userRepo.save(user.get());
        } catch (Exception e) {
            throw new DatabaseErrorException(e.getMessage());
        }
        return new ApiResponseDTO<String>(
                true,
                "Username updated successfully",
                null);
    }

    public ApiResponseDTO<String> updateEmail(UpdateEmailDTO emailDTO, HttpServletRequest request) {
        //clean the data
        String newEmail = emailDTO.getNewEmail().strip().toLowerCase();
        String oldEmail = requestUtil.extractEmailClaim(request);

        //validate the input some more
        if (newEmail.length() > MAX_EMAIL_LENGTH) {
            throw new IllegalArgumentException("Email is too long");
        }

        //get the user from the db
        Optional<UserTable> user = userRepo.findByEmail(oldEmail);

        //update the user info
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find a user to update");
        }

        if(user.get().getEmail().equals(newEmail)){
            throw new ResourceAlreadyExistsException("No changes made. The new email matches the current email.");
        }else{
            user.get().setEmail(newEmail);
        }

        //save the user
        try {
            userRepo.save(user.get());
        } catch (Exception e) {
            throw new DatabaseErrorException(e.getMessage());
        }
        // new token gets minted after email is updated, since email is part of the claims and main way of IDing users
        return new ApiResponseDTO<String>(
                true,
                "Email updated successfully",
                jwtService.generateToken(newEmail));
    }

    public ApiResponseDTO<String> updatePassword(UpdateUserInfoDTO passwordDTO, HttpServletRequest request) {
        //clean the data
        String pswrd = passwordDTO.getNewData().strip();
        String userEmail = requestUtil.extractEmailClaim(request);

        //find user by email
        Optional<UserTable> user = userRepo.findByEmail(userEmail);

        //hash new password
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("The email address " + userEmail + " does not match any existing user account. \n" +
                    "Password updates require a valid email");
        } else {
            user.get().setUserPassword(passwordEncoder.encode(pswrd));
        }

        //save the user
        try {
            userRepo.save(user.get());
        } catch (Exception e) {
            throw new DatabaseErrorException(e.getMessage());
        }

        return new ApiResponseDTO<String>(
                true,
                "password updated successfully",
                null);
    }

    /// ///////////////////
    /// DELETE METHODS ///
    /// ///////////////////

    public ApiResponseDTO<String> deleteUser(HttpServletRequest request) {
        //clean data
        String userEmail = requestUtil.extractEmailClaim(request);

        //find user with that email
        Optional<UserTable> userToBeDeleted = userRepo.findByEmail(userEmail);

        if (userToBeDeleted.isEmpty()) {
            throw new ResourceNotFoundException("A user associated with email "+userEmail+" could not be found");
        }
        //delete user
        userRepo.delete(userToBeDeleted.get());

        return new ApiResponseDTO<String>(
                true,
                "user " + userToBeDeleted.get().getEmail() + " successfully deleted",
                null);
    }

}
