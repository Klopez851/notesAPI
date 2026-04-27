package com.example.notesAPI.service;

import com.example.notesAPI.dto.EmailDTO;
import com.example.notesAPI.dto.UITemplate.CreateTemplateDTO;
import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.UITemplate.DeleteUITemplateDTO;
import com.example.notesAPI.dto.UITemplate.GetTemplateDTO;
import com.example.notesAPI.dto.UITemplate.UpdateTemplateDTO;
import com.example.notesAPI.errorHandler.DatabaseErrorException;
import com.example.notesAPI.errorHandler.IdNotFoundException;
import com.example.notesAPI.errorHandler.ForbiddenRequestException;
import com.example.notesAPI.errorHandler.ResourceNotFoundException;
import com.example.notesAPI.model.UITemplate;
import com.example.notesAPI.model.UserTable;
import com.example.notesAPI.repository.UITemplateRepository;
import com.example.notesAPI.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UITemplateService {

    private final UITemplateRepository templateRepo;
    private final UserRepository userRepo;
    private final JWTService jwtService;

    ////////////////////
    /// POST METHODS ///
    ////////////////////

    public ApiResponseDTO<String> createTemplate(CreateTemplateDTO templateDTO, HttpServletRequest request){
        //clean data
        String email = templateDTO.getEmail().strip().toLowerCase();
        String templateName = templateDTO.getTemplateName().strip();
        String templateDetails = templateDTO.getTemplateDetails().strip();//need to figure this one out, idk how to store template deets yet

        //ensure person making the request and the user creating the template match
        if(isRequestValid(email,request)) {
            //find user in db
            Optional<UserTable> user = userRepo.findByEmail(email);
            if (!user.isPresent()) {
                throw new ResourceNotFoundException("please provide a valid email");
            }

            //create a template
            UITemplate template = new UITemplate();
            template.setUser(user.get());
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
        throw new ForbiddenRequestException("Access denied: You can only modify your own account.");
    }

    ///////////////////
    /// GET METHODS ///
    ///////////////////

    public ApiResponseDTO<List<GetTemplateDTO>> getTemplates(EmailDTO userEmail, HttpServletRequest request) {
        //clean data
        String email = userEmail.getEmail().strip().toLowerCase();

        //validate the request
        if (isRequestValid(email,request)){
            //ensure user exists
            Optional<UserTable> user = userRepo.findByEmail(email);

            if(user.isPresent()){
                //get templates associated with user
                List<GetTemplateDTO> templates = templateRepo.findAllByUser(user.get().getUserID());
                return new ApiResponseDTO<List<GetTemplateDTO>>(true, "templates found", templates);

            }else{throw new ResourceNotFoundException("User associated with that email could not be found");}

        }
        throw new ForbiddenRequestException("Access denied: You can only get information from your own account.");
    }

    ///////////////////
    /// PATCH METHODS ///
    ///////////////////

    public ApiResponseDTO<String> updateTemplateDetails(UpdateTemplateDTO templateDTO, HttpServletRequest request) {
        //clean data
        String email = templateDTO.getEmail().strip().toLowerCase();
        String newTemplateDetails = templateDTO.getNewInfo().strip();
        int templateID = Integer.parseInt(templateDTO.getTemplateID());

        //validate request
        if(isRequestValid(email, request)){
            //ensure the templateDTO being updated belongs to the user making request
            Optional<UITemplate> template = templateRepo.findById(templateID);
            Optional<UserTable> user = userRepo.findByEmail(email);

            if(template.isPresent()){
                if(user.isPresent()){
                    if(user.get().getUserID() == template.get().getUser().getUserID()){
                        //save new template details
                        template.get().setTemplateDetails(newTemplateDetails);
                        templateRepo.save(template.get());

                        return new ApiResponseDTO<String>(true,
                                "template successfully updated", template.get().toString());

                    }else{throw new ResourceNotFoundException("Could not find ui template associated with that user");}
                }else{throw new ResourceNotFoundException("UA user associated with that email could not be found");}
            }else {throw new ResourceNotFoundException("A template associated with that ID could not be found");}

        }
        throw new ForbiddenRequestException("Access denied: You can only modify your own account.");
    }

    //////////////////////
    /// DELETE METHODS ///
    //////////////////////

    public ApiResponseDTO<String> deleteTemplate(DeleteUITemplateDTO template, HttpServletRequest request) {
        //clean data
        String email = template.getEmail().strip().toLowerCase();
        int templateID = Integer.parseInt(template.getTemplateID());

        //ensure request is valid (user making the request and user deleting the template are the same)
        if(isRequestValid(email,request)) {
            //ensure template exists and is associated with the user making the request
            Optional<UserTable> user = userRepo.findByEmail(email);
            Optional<UITemplate> uiTemplate = templateRepo.findById(templateID);

            //delete template if the template exists, the user making the request exist, and if the template to be
            // deleted is associated with the user making the request
            if(uiTemplate.isPresent()){
                if(user.isPresent()){
                    if(uiTemplate.get().getUser().getUserID() == user.get().getUserID()){
                        //delete template
                        templateRepo.deleteById(uiTemplate.get().getTemplateID());

                        //return a response
                        return new ApiResponseDTO<>(true, "template succesfully deleted", null);

                    }else{
                        throw new ResourceNotFoundException("Could not find a UI template associated with that user");
                    }
                }else{
                    throw new ResourceNotFoundException("A user associated with that email could not be found");
                }
            }else{
                throw new IdNotFoundException("A template associated with that ID could not be found");
            }
        }
        throw new ForbiddenRequestException("Access denied: You can only modify your own account.");
    }

    ///////////////////////
    /// PRIVATE METHODS ///
    ///////////////////////

    private boolean isRequestValid(String userEmail, HttpServletRequest request){
        String token;
        String JWTemail = null;

        //get auth header from request
        String authHeader = request.getHeader("Authorization");

        //ensure header isn't empty or wrongly formatted
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            //extract token and get email from token
            token = authHeader.substring(7);//jwt string starts at 7th index of header string
            JWTemail = jwtService.extractEmail(token);
        }

        //ensure emails match
        if (userEmail.equals(JWTemail)){
            return true;
        }

        return false;
    }
}

