package com.example.notesAPI.service;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.UITemplate.CreateTemplateDTO;
import com.example.notesAPI.dto.UITemplate.DeleteUITemplateDTO;
import com.example.notesAPI.dto.UITemplate.GetTemplateDTO;
import com.example.notesAPI.dto.UITemplate.UpdateTemplateDTO;
import com.example.notesAPI.errorHandler.DatabaseErrorException;
import com.example.notesAPI.errorHandler.IdNotFoundException;
import com.example.notesAPI.errorHandler.ResourceNotFoundException;
import com.example.notesAPI.model.UITemplate;
import com.example.notesAPI.model.UserTable;
import com.example.notesAPI.repository.UITemplateRepository;
import com.example.notesAPI.repository.UserRepository;
import com.example.notesAPI.utilClasses.RequestValidationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UITemplateService {
    /// CONSTANTS ///
    private final int MAX_TEMPLATE_NAME_SIZE = 25;
    private final UITemplateRepository templateRepo;
    private final UserRepository userRepo;
    private final RequestValidationService requestUtil;

    /// /////////////////
    /// POST METHODS ///
    /// /////////////////

    public ApiResponseDTO<String> createTemplate(CreateTemplateDTO templateDTO, HttpServletRequest request) {
        //clean data
        String email = requestUtil.extractEmailClaim(request);
        String templateName = templateDTO.getTemplateName().strip();
        String templateDetails = templateDTO.getTemplateDetails().strip();//need to figure this one out, idk how to store template deets yet

        if (templateName.length() > MAX_TEMPLATE_NAME_SIZE) {
            throw new IllegalArgumentException("template name can be a maximum of " + MAX_TEMPLATE_NAME_SIZE + " characters");
        }

        //find user in db
        Optional<UserTable> user = userRepo.findByEmail(email);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("A user associated with the email " + email + " could not be found");
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
                null
        );
    }

    /// ////////////////
    /// GET METHODS ///
    /// ////////////////

    public ApiResponseDTO<List<GetTemplateDTO>> getTemplates(HttpServletRequest request) {
        //get data
        String email = requestUtil.extractEmailClaim(request);

        //ensure user exists
        Optional<UserTable> user = userRepo.findByEmail(email);

        if (user.isPresent()) {
            //get templates associated with user
            List<GetTemplateDTO> templates = templateRepo.findAllByUser(user.get().getUserID());
            return new ApiResponseDTO<List<GetTemplateDTO>>(true, "templates found", templates);

        } else {
            throw new ResourceNotFoundException("A user associated with the email " + email + " could not be found");
        }

    }

    /// //////////////////
    /// PATCH METHODS ///
    /// //////////////////

    public ApiResponseDTO<String> updateTemplateDetails(UpdateTemplateDTO templateDTO, HttpServletRequest request) {
        //clean data
        String email = requestUtil.extractEmailClaim(request);
        String newTemplateDetails = templateDTO.getNewInfo().strip();
        int templateID = Integer.parseInt(templateDTO.getTemplateID());

        //ensure the template being updated belongs to the user making request
        Optional<UITemplate> template = templateRepo.findById(templateID);
        Optional<UserTable> user = userRepo.findByEmail(email);

        if (template.isPresent()) {
            if (user.isPresent()) {
                if (user.get().getUserID() == template.get().getUser().getUserID()) {
                    //save new template details
                    template.get().setTemplateDetails(newTemplateDetails);
                    templateRepo.save(template.get());

                    return new ApiResponseDTO<String>(true,
                            "template successfully updated", null);

                } else {
                    throw new ResourceNotFoundException("Could not find ui template associated with that user");
                }
            } else {
                throw new ResourceNotFoundException("A user associated with the email " + email + " could not be found");
            }
        } else {
            throw new ResourceNotFoundException("A template associated with that ID could not be found");
        }

    }

    public ApiResponseDTO<String> updateTemplateName(UpdateTemplateDTO templateDTO, HttpServletRequest request) {
        //clean data
        String email = requestUtil.extractEmailClaim(request);
        String newName = templateDTO.getNewInfo().strip();
        int templateID = Integer.parseInt(templateDTO.getTemplateID().strip());

        //input validation
        if (newName.length() > MAX_TEMPLATE_NAME_SIZE) {
            throw new IllegalArgumentException("template name can be a maximum of " + MAX_TEMPLATE_NAME_SIZE + " characters");
        }

        //ensure template exists
        Optional<UITemplate> template = templateRepo.findById(templateID);

        //ensure email is valid
        Optional<UserTable> user = userRepo.findByEmail(email);

        //ensure template is associated with the email/user provided
        if (template.isPresent()) {
            if (user.isPresent()) {
                if (template.get().getUser().getUserID() == user.get().getUserID()) {
                    //update and save template
                    template.get().setTemplateName(newName);

                    templateRepo.save(template.get());

                    return new ApiResponseDTO<String>(
                            true,
                            "Template name successfully updated",
                            null);

                } else {
                    throw new ResourceNotFoundException("such template associated with the user could not be found");
                }
            } else {
                throw new ResourceNotFoundException("A user associated with the email " + email + " could not be found");
            }
        } else {
            throw new ResourceNotFoundException("A template with that ID could not found");
        }
    }

    /// ///////////////////
    /// DELETE METHODS ///
    /// ///////////////////

    public ApiResponseDTO<String> deleteTemplate(DeleteUITemplateDTO template, HttpServletRequest request) {
        //clean data
        String email = requestUtil.extractEmailClaim(request);
        int templateID = Integer.parseInt(template.getTemplateID());

        //ensure template exists and is associated with the user making the request
        Optional<UserTable> user = userRepo.findByEmail(email);
        Optional<UITemplate> uiTemplate = templateRepo.findById(templateID);

        //delete template if the template exists, the user making the request exist, and if the template to be
        // deleted is associated with the user making the request
        if (uiTemplate.isPresent()) {
            if (user.isPresent()) {
                if (uiTemplate.get().getUser().getUserID() == user.get().getUserID()) {
                    //delete template
                    templateRepo.deleteById(uiTemplate.get().getTemplateID());

                    //return a response
                    return new ApiResponseDTO<>(
                            true,
                            "template succesfully deleted",
                            null);

                } else {
                    throw new ResourceNotFoundException("Could not find a UI template associated with that user");
                }
            } else {
                throw new ResourceNotFoundException("A user associated with that email could not be found");
            }
        } else {
            throw new IdNotFoundException("A template associated with that ID could not be found");
        }
    }

}

