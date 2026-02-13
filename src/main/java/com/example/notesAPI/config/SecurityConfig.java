package com.example.notesAPI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //tells spring to use this config rather than default
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf();
        http.authorizeHttpRequests(request -> request.anyRequest().authenticated()); // this makes it so that ALL requests need to be authenticated
        http.httpBasic(Customizer.withDefaults()); // requires login w/ postman


        return http.build(); //returns a securityFilterChainObject
    }
}
