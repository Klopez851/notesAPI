package com.example.notesAPI.service;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.User.UserInfoDTO;
import com.example.notesAPI.errorHandler.UserAlreadyExistsException;
import com.example.notesAPI.errorHandler.UserNotFoundException;
import com.example.notesAPI.model.UserTable;
import com.example.notesAPI.repository.userRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final int MAX_USERNAME_LENGTH = 50;
    private final int MAX_EMAIL_LENGTH = 254;


    public ApiResponseDTO<String> createUser(UserInfoDTO userDTO) {
        //clean the data
        String username = userDTO.getUsername().trim();
        String email = userDTO.getEmail().trim().toLowerCase(); //to have consistency in email format in db
        String password = userDTO.getUserPassword().trim();

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
        userRepo.save(user);

        return new ApiResponseDTO<>(true,"user created successfully", user.toString());
    }

    public String verify(UserInfoDTO user) {
        Authentication authenticate =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getUserPassword()));
                //auth manager returns an Authentication object and takes type authentication token
        if(authenticate.isAuthenticated()){
            return jwtService.generateToken(user.getEmail());
        }
        else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public ApiResponseDTO<String> updateUsername(String newUsername, String email) {
       //clean the data
        String username = newUsername.trim();
        String userEmail = email.trim();

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
        userRepo.save(user);

        return new ApiResponseDTO<String>(true,"Username updated successfully",user.toString() );
    }

    public ApiResponseDTO<String> updateEmail(String oldEmail, String newEmail) {
        //clean the data
        String email = newEmail.trim();
        String tempEmail = oldEmail.trim();

        //validate the input some more
        if(email.length() > MAX_EMAIL_LENGTH){
            throw new IllegalArgumentException("Email is too long");
        }

        //get the user from the db
        UserTable user = userRepo.findByEmail(tempEmail);

        //update the user info
        if(user == null){
            throw new UserNotFoundException("Cannot find a user with email");
        }else{
            user.setEmail(email);
        }
        //save the user
        userRepo.save(user);

        return new ApiResponseDTO<String>(true,"Email updated successfully",user.toString() );
    }

    public ApiResponseDTO<String> updatePassword(String newPassword, String email) {
        //clean the data
        String pswrd = newPassword.trim();
        String userEmail = email.trim();

        //find user by email
        UserTable user = userRepo.findByEmail(userEmail);

        //hash new password
        if(user ==  null){
            throw new UserNotFoundException("The email address provided does not match any existing user account. " +
                    "Password updates require a valid email to identify the user record to update.");
        }else {
            user.setUserPassword(passwordEncoder.encode(pswrd));
        }

        //save the user
        userRepo.save(user);

        return new ApiResponseDTO<String>(true,"Email updated successfully",user.toString() );
    }
}
