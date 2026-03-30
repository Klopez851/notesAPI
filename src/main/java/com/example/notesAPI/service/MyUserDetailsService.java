package com.example.notesAPI.service;

import com.example.notesAPI.model.MyUserDetails;
import com.example.notesAPI.model.UserTable;
import com.example.notesAPI.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private userRepository userRepo;

//    public MyUserDetailsService(userRepository userRepo){
//        this.userRepo=userRepo;
//    }

    @Override
    //the email is the users username
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserTable user = userRepo.findByEmail(email) ;
        if(user == null){
            throw  new UsernameNotFoundException("user not found");
        }

        return new MyUserDetails(user);
    }
}
