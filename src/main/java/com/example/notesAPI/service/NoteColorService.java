package com.example.notesAPI.service;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.EmailDTO;
import com.example.notesAPI.dto.noteColor.CreateNoteColorDTO;
import com.example.notesAPI.dto.noteColor.NoteColorDTO;
import com.example.notesAPI.errorHandler.ForbiddenRequestException;
import com.example.notesAPI.errorHandler.ResourceAlreadyExistsException;
import com.example.notesAPI.errorHandler.ResourceNotFoundException;
import com.example.notesAPI.model.NoteColor;
import com.example.notesAPI.model.UserTable;
import com.example.notesAPI.repository.NoteColorRepository;
import com.example.notesAPI.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.UserTransaction;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
                    noteColorRepo.save(color);
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

    //update color hex

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
