package com.example.notesAPI.service;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.EmailDTO;
import com.example.notesAPI.dto.noteColor.CreateNoteColorDTO;
import com.example.notesAPI.dto.noteColor.NoteColorDTO;
import com.example.notesAPI.dto.noteColor.UpdateNoteColorDTO;
import com.example.notesAPI.errorHandler.DatabaseErrorException;
import com.example.notesAPI.errorHandler.ForbiddenRequestException;
import com.example.notesAPI.errorHandler.ResourceAlreadyExistsException;
import com.example.notesAPI.errorHandler.ResourceNotFoundException;
import com.example.notesAPI.model.NoteColor;
import com.example.notesAPI.model.UserTable;
import com.example.notesAPI.repository.NoteColorRepository;
import com.example.notesAPI.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NoteColorService {

    private final UserRepository userRepo;
    private final NoteColorRepository noteColorRepo;
    private final JWTService jwtService;

    /////////////////////
    /// POST METHOD/S ///
    /////////////////////

    public ApiResponseDTO<String> createNoteColor(CreateNoteColorDTO colorDTO, HttpServletRequest request) {
        //clean data
        String email = colorDTO.getEmail().strip().toLowerCase();
        String colorHex= colorDTO.getColorHex().strip();

        //validate request
        if (isRequestValid(email,request)){
            //ensure email is associated with an existing user
            Optional<UserTable> user = userRepo.findByEmail(email);

            if(user.isPresent()){
                //create noteColor
                NoteColor color = new NoteColor(colorHex, user.get());

                if(!noteColorRepo.existsByColorHEX(colorHex)) {
                    //store color
                    try {
                        noteColorRepo.save(color);
                    } catch (Exception e) {
                        throw new DatabaseErrorException(e.getMessage());
                    }
                    return new ApiResponseDTO<String>(true, "color successfully saved", color.toString());

                }throw new ResourceAlreadyExistsException("Such color already exists associated with that email");
            }throw new ResourceNotFoundException("A user associated with that email could not be found");
        }throw new ForbiddenRequestException("Access denied: You can only modify your own account.");
    }

    ////////////////////
    /// GET METHOD/S ///
    ////////////////////

    public ApiResponseDTO<List<NoteColorDTO>> getNoteColors(EmailDTO emailDTO, HttpServletRequest request) {
        //clean data
        String email = emailDTO.getEmail().strip().toLowerCase();

        //validate request
        if(isRequestValid(email,request)){
            //ensure user exists
            Optional<UserTable> user = userRepo.findByEmail(email);

            if(user.isPresent()){
                //fetch and return all associated colors
                return new ApiResponseDTO<List<NoteColorDTO>>(
                        true,
                        "Colors succesfully fetched",
                        noteColorRepo.findAllByUser(user.get().getUserID())
                );

            }throw new ResourceNotFoundException("A user associated with that email could not be found");
        }throw new ForbiddenRequestException("Access denied: You can only modify your own account.");

    }

    //////////////////////
    /// PATCH METHOD/S ///
    //////////////////////

    public ApiResponseDTO<String> updateNoteColor(UpdateNoteColorDTO colorDTO, HttpServletRequest request) {
        //clean data
        String email = colorDTO.getEmail().strip().toLowerCase();
        String newColor = colorDTO.getNewColor().strip();
        int colorID = Integer.parseInt(colorDTO.getColorID().strip());

        //validate request
        if(isRequestValid(email,request)){
            //get color to be updated
            Optional<NoteColor> color = noteColorRepo.findById(colorID);

            //get user
            Optional<UserTable> user = userRepo.findByEmail(email);

            //ensure color to be updates is associated with the provided user
            if(color.isPresent()){
                if(user.isPresent()){
                    if(user.get().getUserID() == color.get().getUser().getUserID()){
                        //update and save color
                        color.get().setColorHEX(newColor);

                        try {
                            noteColorRepo.save(color.get());
                        } catch (Exception e) {
                            throw new DatabaseErrorException(e.getMessage());
                        }

                        return new ApiResponseDTO<String>(
                                true,
                                "color successfully updated",
                                null
                        );

                    }else{throw new ResourceNotFoundException("A color by that ID associated with the provided email could not be found");}
                }else{throw new ResourceNotFoundException("A user associated with that email could not be found");}
            }else {throw new ResourceNotFoundException("A color associated with that ID could not be found");}
        }throw new ForbiddenRequestException("Access denied: You can only modify your own account.");
    }

    ///////////////////////
    /// DELETE METHOD/S ///
    ///////////////////////

    //delete note color

    ////////////////////////
    /// PRIVATE METHOD/S ///
    ////////////////////////

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
