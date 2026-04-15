package com.example.notesAPI.service;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.User.*;
import com.example.notesAPI.errorHandler.DatabaseErrorException;
import com.example.notesAPI.errorHandler.InvalidRequestException;
import com.example.notesAPI.errorHandler.UserAlreadyExistsException;
import com.example.notesAPI.errorHandler.UserNotFoundException;
import com.example.notesAPI.model.UserTable;
import com.example.notesAPI.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
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

    // CONSTANTS //
    private final int MAX_USERNAME_LENGTH = 50;
    private final int MAX_EMAIL_LENGTH = 254;

    //using constructor injection with lombok annotations
    private UserRepository userRepo;
    private BCryptPasswordEncoder passwordEncoder;
    private AuthenticationManager  authManager;
    private JWTService jwtService;

    ////////////////////
    /// POST METHODS ///
    ////////////////////

    public String verify(UserLoginDTO user) {
        Authentication authenticate =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getUserPassword()));
        //auth manager returns an Authentication object and takes type authentication token
        if(authenticate.isAuthenticated()){
            return jwtService.generateToken(user.getEmail());
        }
        else {
            throw new UserNotFoundException("User not found with the email "+ user.getEmail());
        }
    }

    public ApiResponseDTO<String> createUser(UserInfoDTO userDTO) {
        //clean the data
        String username = userDTO.getUsername().strip();
        String email = userDTO.getEmail().strip().toLowerCase(); //to have consistency in email format in db
        String password = userDTO.getUserPassword().strip();

        //validate username length
        if(username.length() > MAX_USERNAME_LENGTH){
            throw new IllegalArgumentException("Username is too long, needs to be less than 51 characters");
        }

        //validate email length
        if(email.length() > MAX_EMAIL_LENGTH){
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
        try {
            userRepo.save(user);
        } catch (Exception e) {
            throw new DatabaseErrorException(e.getMessage());
        }

        return new ApiResponseDTO<>(true,"user created successfully", user.toString());
    }

    ////////////////////
    /// GET METHODS ///
    ////////////////////

    public ApiResponseDTO<UserInfoDTO> getUser(String userEmail) {
        //clean the data
        String email = userEmail.strip().toLowerCase();

        UserInfoDTO userInfo;

        //get the user from the db
        UserTable user = userRepo.findByEmail(email);
        //make sure the user exists
        if(user == null){
            throw new UserNotFoundException("User with the email "+email+" not found");
        }else{
            userInfo = new UserInfoDTO(user.getUsername(),user.getEmail(),null);
        }
        //send back the user dto
        return new ApiResponseDTO<UserInfoDTO>(true,"User Found", userInfo);
    }

    /////////////////////
    /// PATCH METHODS ///
    /////////////////////

    public ApiResponseDTO<String> updateUsername(UpdateUserInfoDTO userInfoDTO, HttpServletRequest request) {
       //clean the data
        String username = userInfoDTO.getNewData().strip();
        String userEmail = userInfoDTO.getEmail().strip().toLowerCase();

        if(isRequestValid(userEmail, request)) {
            //validate the input some more
            if(username.length() > MAX_USERNAME_LENGTH){
                throw new IllegalArgumentException("Username is too long");
            }

            //get the user from the db
            UserTable user = userRepo.findByEmail(userEmail);

            //update the user info
            if(user == null){
                throw new UserNotFoundException("The email address provided does not match any existing user account. " +
                        "Username updates require a valid email to identify the user record to update.");
            }else {
                user.setUsername(username);
            }
            //save the user
            try {
                userRepo.save(user);
            } catch (Exception e) {
                throw new DatabaseErrorException(e.getMessage());
            }

            return new ApiResponseDTO<String>(true,"Username updated successfully",user.toString() );
        }

        throw new InvalidRequestException("Access denied: You can only modify your own account.");
    }

    public ApiResponseDTO<String> updateEmail(UpdateEmailDTO emailDTO, HttpServletRequest request) {
        //clean the data
        String newEmail = emailDTO.getNewEmail().strip().toLowerCase();
        String oldEmail = emailDTO.getOldEmail().strip().toLowerCase();

        if(isRequestValid(oldEmail, request)) {
            //validate the input some more
            if (newEmail.length() > MAX_EMAIL_LENGTH) {
                throw new IllegalArgumentException("Email is too long");
            }

            //get the user from the db
            UserTable user = userRepo.findByEmail(oldEmail);

            //update the user info
            if (user == null) {
                throw new UserNotFoundException("Cannot find a user with newEmail");
            } else {
                user.setEmail(newEmail);
            }
            //save the user
            try {
                userRepo.save(user);
            } catch (Exception e) {
                throw new DatabaseErrorException(e.getMessage());
            }

            // new token gets minted after email is updated, since email is part of the claims and main way of IDing users
            return new ApiResponseDTO<String>(true, "Email updated successfully", jwtService.generateToken(newEmail));
        }

        throw new InvalidRequestException("Access denied: You can only modify your own account.");
    }

    public ApiResponseDTO<String> updatePassword(UpdateUserInfoDTO passwordDTO, HttpServletRequest request) {
        //clean the data
        String pswrd = passwordDTO.getNewData().strip();
        String userEmail = passwordDTO.getEmail().strip().toLowerCase();

        if(isRequestValid(userEmail, request)) {
            //find user by email
            UserTable user = userRepo.findByEmail(userEmail);

            //hash new password
            if(user ==  null){
                throw new UserNotFoundException("The email address " +userEmail+" does not match any existing user account. " +
                        "Password updates require a valid email to identify the user record to update.");
            }else {
                user.setUserPassword(passwordEncoder.encode(pswrd));
            }

            //save the user
            try {
                userRepo.save(user);
            } catch (Exception e) {
                throw new DatabaseErrorException(e.getMessage());
            }

            return new ApiResponseDTO<String>(true,"password updated successfully",user.toString());
        }
        throw new InvalidRequestException("Access denied: You can only modify your own account.");
    }

    //////////////////////
    /// DELETE METHODS ///
    //////////////////////

    public ApiResponseDTO<String> deleteUser(GetUserDTO user, HttpServletRequest request) {
        //clean data
        String userEmail = user.getEmail().strip().toLowerCase();

        if(isRequestValid(userEmail, request)) {
            //find user with that email
            UserTable userToBeDeleted = userRepo.findByEmail(userEmail);

            if(userToBeDeleted == null){
                throw new UserNotFoundException("A user associated with that email could not be found");
            }
            //delete user
            userRepo.delete(userToBeDeleted);

            return new ApiResponseDTO<String>(true,"user "+userToBeDeleted.getEmail()+" successfully deleted", null);
        }
        throw new InvalidRequestException("Access denied: You can only modify your own account.");
    }

    ///////////////////////
    /// PRIVATE METHODS ///
    ///////////////////////

    private boolean isRequestValid(String userEmail, HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        String token;
        String JWTemail = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);//jwt string starts at 7th index of header string
            JWTemail = jwtService.extractEmail(token);
        }

        if (userEmail.equals(JWTemail)){
            return true;
        }

        return false;
    }
}
