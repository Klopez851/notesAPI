package com.example.notesAPI.service;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.Label.LabelDTO;
import com.example.notesAPI.errorHandler.IdNotFoundException;
import com.example.notesAPI.errorHandler.ForbiddenRequestException;
import com.example.notesAPI.errorHandler.ResourceNotFoundException;
import com.example.notesAPI.model.Label;
import com.example.notesAPI.model.UserTable;
import com.example.notesAPI.repository.LabelRepository;
import com.example.notesAPI.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LabelService {

    private final LabelRepository labelRepo;
    private final UserRepository userRepo;
    private final JWTService jwtService;

    ////////////////////
    /// POST METHODS ///
    ////////////////////

    public ApiResponseDTO<String> createLabel(HashMap<String, String> userLabel, HttpServletRequest request){
        Label label;

        //clean data
        String labelName = userLabel.get("label").strip();
        String email =userLabel.get("email").strip().toLowerCase();

        if(isRequestValid(email, request)) {
            //find user
            UserTable user = userRepo.findByEmail(email);

            // get user
            if (user == null) {
                throw new IllegalArgumentException("Valid email needed to create label");
            } else {
                label = new Label(user, labelName);
            }

            //store label
            labelRepo.save(label);

            return new ApiResponseDTO<>(true, "label successfully created", label.toString());
        }
        throw new ForbiddenRequestException("Access denied: You can only modify your own account.");
    }

    ///////////////////
    /// GET METHODS ///
    ///////////////////

    public ApiResponseDTO<List<LabelDTO>> getLabels(HashMap<String, String> userEmail, HttpServletRequest request) {
        //clean email
        String email = userEmail.get("email").strip().toLowerCase();

        if(isRequestValid(email, request)) {
            //get user
            UserTable user = userRepo.findByEmail(email);

            if (user == null) {
                throw new ResourceNotFoundException("Please provide a valid email to fetch labels");
            }

            //get all labels associated with that user
            List<LabelDTO> labels = labelRepo.findAllByUser(user.getUserID());

            //return them
            return new ApiResponseDTO<List<LabelDTO>>(true, "labels successfully fetched", labels);
        }
        throw new ForbiddenRequestException("Access denied: You can only request information from your own account.");
    }

    /////////////////////
    /// PATCH METHODS ///
    /////////////////////

    public ApiResponseDTO<String> updateLabel(HashMap<String, String> reqLabel, HttpServletRequest request) {
        //clean data
        String reqLabelName = reqLabel.get("labelName").strip();
        int reqLabelID = Integer.parseInt(reqLabel.get("labelID"));
        String email = reqLabel.get("email").strip().toLowerCase();

        if(isRequestValid(email, request)) {
            //get label from db
            Optional<Label> label = labelRepo.findById(reqLabelID);

            //make sure label isnt the same
            if (label.isEmpty()) {
                throw new IdNotFoundException("A label with that ID doesnt exist");
            }

            //make sure the labels are the same
            if (Objects.equals(label.get().getLabelName(), reqLabelName)) {
                return new ApiResponseDTO<String>(true,
                        "The label name in your request matches the existing name in the database.", null);
            }
            //update label
            label.get().setLabelName(reqLabelName);

            //save label
            labelRepo.save(label.get());

            return new ApiResponseDTO<String>(true, "Label successfully updated",
                    label.get().getLabelName());
        }
        throw new ForbiddenRequestException("Access denied: You can only modify your own account.");
    }

    //////////////////////
    /// DELETE METHODS ///
    //////////////////////

    public ApiResponseDTO<String> deleteLabel(HashMap<String, String> reqlabel, HttpServletRequest request) {
        //clean/format data
        String email = reqlabel.get("email").strip().toLowerCase();
        int labelID = Integer.parseInt(reqlabel.get("labelID").strip());

        if(isRequestValid(email, request)) {
            //ensure label and user exists
            Optional<Label> label = labelRepo.findById(labelID);
            UserTable user = userRepo.findByEmail(email);

            //delete user if label exists, if user exists, and is label is associated with the user
            if(label.isPresent()){
                if(user != null){
                    if(label.get().getUser().getUserID() == user.getUserID()){
                        //delete label
                        labelRepo.delete(label.get());
                        return new ApiResponseDTO<String>(true, "label successfully deleted", null);

                    }else{throw new ResourceNotFoundException("Could not find label associated with that user");}
                }else{throw new ResourceNotFoundException("A user with that email could not be found");}
            }else{throw new IdNotFoundException("A label with that id could not be found");}
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
