package com.example.notesAPI.service;

import com.example.notesAPI.dto.UITemplate.CreateTemplateDTO;
import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.UITemplate.GetTemplateDTO;
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
            UserTable user = userRepo.findByEmail(email);
            if (user == null) {
                throw new ResourceNotFoundException("please provide a valid email");
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
        throw new ForbiddenRequestException("Access denied: You can only modify your own account.");
    }

    ///////////////////
    /// GET METHODS ///
    ///////////////////

    public ApiResponseDTO<List<GetTemplateDTO>> getTemplates(HashMap<String, String> userEmail, HttpServletRequest request) {
        //clean data
        String email = userEmail.get("email").strip().toLowerCase();

        //validate the request
        if (isRequestValid(email,request)){
            //ensure user exists
            UserTable user = userRepo.findByEmail(email);

            if(user != null){
                //get templates associated with user
                List<GetTemplateDTO> templates = templateRepo.findAllByUser(user.getUserID());
                return new ApiResponseDTO<List<GetTemplateDTO>>(true, "templates found", templates);

            }else{throw new ResourceNotFoundException("User associated with that email could not be found");}

        }
        throw new ForbiddenRequestException("Access denied: You can only get information from your own account.");
    }

    //////////////////////
    /// DELETE METHODS ///
    //////////////////////

    public ApiResponseDTO<String> deleteTemplate(HashMap<String, String> template, HttpServletRequest request) {
        //clean data
        String email = template.get("email").strip().toLowerCase();
        int templateID = Integer.parseInt(template.get("templateID"));

        //ensure request is valid (user making the request and user deleting the template are the same)
        if(isRequestValid(email,request)) {
            //ensure template exists and is associated with the user making the request
            UserTable user = userRepo.findByEmail(email);
            Optional<UITemplate> uiTemplate = templateRepo.findById(templateID);

            //delete template if the template exists, the user making the request exist, and if the template to be
            // deleted is associated with the user making the request
            if(uiTemplate.isPresent()){
                if(user != null){
                    if(uiTemplate.get().getUser().getUserID() == user.getUserID()){
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

