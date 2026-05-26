package com.example.notesAPI.utilClasses;

import com.example.notesAPI.service.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RequestValidationService {

    private JWTService jwtService;

    public boolean isRequestValid(String userEmail, HttpServletRequest request) {
        String token;
        String JWTemail = null;

        //get auth header from request
        String authHeader = request.getHeader("Authorization");

        //ensure header isn't empty or wrongly formatted
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            //extract token and get email from token
            token = authHeader.substring(7);//jwt string starts at 7th index of header string
            JWTemail = jwtService.extractEmail(token);
        }

        //ensure emails match
        if (userEmail.equals(JWTemail)) {
            return true;
        }

        return false;
    }

    public String extractEmailClaim(HttpServletRequest request) {
        String token;
        String JWTemail = null;

        //get auth header from request
        String authHeader = request.getHeader("Authorization");

        //ensure header isn't empty or wrongly formatted
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            //extract token and get email from token
            token = authHeader.substring(7);//jwt string starts at 7th index of header string
            JWTemail = jwtService.extractEmail(token);
        }

        return JWTemail;
    }
}
