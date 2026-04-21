package com.example.notesAPI.service;

import com.example.notesAPI.errorHandler.ResourceNotFoundException;
import com.example.notesAPI.model.MyUserDetails;
import com.example.notesAPI.model.UserTable;
import com.example.notesAPI.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepo;

    @Override
    //the email is the users username
    public UserDetails loadUserByUsername(String email) throws ResourceNotFoundException {
        UserTable user = userRepo.findByEmail(email) ;
        if(user == null){
            throw  new ResourceNotFoundException("user not found");
        }

        return new MyUserDetails(user);
    }
}
