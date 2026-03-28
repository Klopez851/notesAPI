package com.example.notesAPI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class EncryptionConfig {

    //encoder is a misnomer, in reality it hashes the passwords
    @Bean// BCrypt is thread-safe and stateless so no need to scope this bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder(10);//how many times the password is hashed (so how many times the alg gets ran on the results)
    }
}
