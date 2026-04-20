package com.example.notesAPI.service;

import com.example.notesAPI.dto.UITemplate.CreateTemplateDTO;
import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.errorHandler.DatabaseErrorException;
import com.example.notesAPI.errorHandler.InvalidRequestException;
import com.example.notesAPI.errorHandler.UserNotFoundException;
import com.example.notesAPI.model.UITemplate;
import com.example.notesAPI.model.UserTable;
import com.example.notesAPI.repository.UITemplateRepository;
import com.example.notesAPI.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import java.util.WeakHashMap;

@Service
@AllArgsConstructor
public class UITemplateService {

    private final UITemplateRepository templateRepo;
    private final UserRepository userRepo;
    private final JWTService jwtService;

    ///////////////////////
    /// POST METHODS ///
    ///////////////////////

    public ApiResponseDTO<String> createTemplate(CreateTemplateDTO templateDTO, HttpServletRequest request){
        //clean data
        String email = templateDTO.getEmail().strip().toLowerCase();
        String templateName = templateDTO.getTemplateName().strip();
        String templateDetails = templateDTO.getTemplateDetails().strip();//need to figure this one out, idk how to store template deets yet

        if(isRequestValid(email,request)) {
            //find user in db
            UserTable user = userRepo.findByEmail(email);
            if (user == null) {
                throw new UserNotFoundException("please provide a valid email");
            }

            //create a template
            UITemplate template = new UITemplate();
            template.setUser(user);
            template.setTemplateName(templateName);
            template.setTemplateDetails(templateDetails);

            //add template to db
            try {
                templateRepo.save(template);
            } catch (Exception e) {
                throw new DatabaseErrorException(e.getMessage());
            }

            return new ApiResponseDTO<>(
                    true,
                    "Template created successfully",
                    template.toString()
            );
        }
        throw new InvalidRequestException("Access denied: You can only modify your own account.");
    }

    ///////////////////////
    /// PRIVATE METHODS ///
    ///////////////////////
    ///
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

