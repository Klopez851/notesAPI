package com.example.notesAPI.service;

import com.example.notesAPI.model.MyUserDetails;
import com.example.notesAPI.repository.userRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private userRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username) ;

        if(user == null){
            System.out.println("user not found");
            throw  new UsernameNotFoundException("user not found");
        }

        return new MyUserDetails(user);
    }
}
