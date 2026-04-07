package com.example.notesAPI.service;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.Label.LabelDTO;
import com.example.notesAPI.errorHandler.UserNotFoundException;
import com.example.notesAPI.model.Label;
import com.example.notesAPI.model.UserTable;
import com.example.notesAPI.repository.LabelRepository;
import com.example.notesAPI.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class LabelService {
    private LabelRepository labelRepo;
    private UserRepository userRepo;

    public ApiResponseDTO<String> createLabel(HashMap<String, String> userLabel){
        Label label;

        //clean label strong
        String labelName = userLabel.get("label").strip();

        //find user
        UserTable user = userRepo.findByEmail(userLabel.get("email"));

        // get user
        if(user == null){
            throw new IllegalArgumentException("Valid email needed to create label");
        }else{
            label = new Label(user,labelName);
        }

        //store label
        labelRepo.save(label);

        return  new ApiResponseDTO<>(true, "label successfully created", label.toString());
    }

    public ApiResponseDTO<List<LabelDTO>> getLabels(HashMap<String, String> userEmail) {
        //clean email
        String email = userEmail.get("email").strip().toLowerCase();

        //get user
        UserTable user = userRepo.findByEmail(email);

        if(user == null){
            throw new UserNotFoundException("Please provide a valid email to fetch labels");
        }

        //get all labels associated with that user
        List<LabelDTO> labels = labelRepo.findAllByUser(user.getUserID());

        //return them
        return new ApiResponseDTO<List<LabelDTO>>(true, "labels successfully fetched", labels);
    }
}
