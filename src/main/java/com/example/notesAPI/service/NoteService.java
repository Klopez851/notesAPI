package com.example.notesAPI.service;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.Note.CreateNoteDTO;
import com.example.notesAPI.errorHandler.DatabaseErrorException;
import com.example.notesAPI.errorHandler.ForbiddenRequestException;
import com.example.notesAPI.errorHandler.ResourceNotFoundException;
import com.example.notesAPI.model.Label;
import com.example.notesAPI.model.Note;
import com.example.notesAPI.model.NoteColor;
import com.example.notesAPI.model.UserTable;
import com.example.notesAPI.repository.LabelRepository;
import com.example.notesAPI.repository.NoteColorRepository;
import com.example.notesAPI.repository.NotesRepository;
import com.example.notesAPI.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NoteService {

    private final NotesRepository noteRepo;
    private final NoteColorRepository noteColorRepo;
    private final UserRepository userRepo;
    private final LabelRepository labelRepo;
    private final JWTService jwtService;

    ////////////////////
    /// POST METHODS ///
    ////////////////////

    public ApiResponseDTO<String> createNote(CreateNoteDTO noteDTO, HttpServletRequest request){
        //clean the data
        String email = noteDTO.getEmail().strip().toLowerCase();
        String title = noteDTO.getTitle().orElse(null).strip();
        String content = noteDTO.getContent().orElse(null).strip();;

        // validate the request
        if(isRequestValid(email, request)){
            Optional<Label> label = null;
            Optional<NoteColor> color = null;

            //look up user
            Optional<UserTable> user = userRepo.findByEmail(email);
            if(user.isEmpty()){
                throw new ResourceNotFoundException("A user associated with that email could not be found");
            }

            //look up label if not null
            if(noteDTO.getLabelID().isPresent()){
                label = labelRepo.findById(noteDTO.getLabelID().get());
            }

            //look up color if not null
            if(noteDTO.getNoteColorID().isPresent()){
                color = noteColorRepo.findById(noteDTO.getNoteColorID().get());
            }

            //create note
            Note note = new Note(user.get(),title,content,label.get() ,color.get());

            //give value to remaining note attributes
            note.setPinned(false);
            note.setHidden(false);
            note.setViewOnly(false);
            note.setDeleted(false);
            note.setCosmetics("<insert cosmetics here");
            note.setTimeLeftBeforeDeletion(null);

            //save note
            try {
                noteRepo.save(note);
            } catch (Exception e) {
                throw new DatabaseErrorException(e.getMessage());
            }

            return new ApiResponseDTO<String>(true, "note successfully created", note.toString());

        }throw new ForbiddenRequestException("Access denied: You can only modify your own account.");
    }

    ///////////////////
    /// GET METHODS ///
    ///////////////////

    ///////////////////
    /// PUT METHODS ///
    ///////////////////

    /////////////////////
    /// PATCH METHODS ///
    /////////////////////

    //////////////////////
    /// DELETE METHODS ///
    //////////////////////

//    // need to figure out how to look for strings, if via id, or if via title, or via text content, so the front end
//    public apiResponseDTO<noteResponseDTO> getNote(int id){
//        Optional<Note> note = repo.findById(id);
//        if(note.isPresent()){
//            return new apiResponseDTO<noteResponseDTO>(
//                    true,
//                    "Note found",
//                    noteResponseDTO.toDTO(note.get())
//            );
//        }
//        else{
//            return new apiResponseDTO<noteResponseDTO>(
//                    false,
//                    "Note not found",
//                    null
//            );
//        }
//    }
//
//    public apiResponseDTO<updateNoteDTO> updateNote(int id,Optional<String> content, Optional<String> title,
//                                                    Optional<String> labelID){
//        //get old note values
//        Optional<Note> tempNote = repo.findById(id);
//        if(tempNote.isPresent()) {
//            Note note = tempNote.get();
//
//            //update whatever needs to be updated;
//            if (title.isPresent()) {
//                note.setTitle(title.get());
//            }
//            if (content.isPresent()) {
//                note.setContent(content.get());
//            }
//            if (labelID.isPresent()) {
//                note.setLabelID(labelID.get());
//            }
//
//            //save time of update
//            note.setUpdatedAt(LocalDateTime.now());
//
//            //save he updated note
//            repo.save(note);
//
//            //send api response
//            return new apiResponseDTO<updateNoteDTO>(
//                    true,
//                    "Note successfully updated",
//                    updateNoteDTO.toDTO(note)
//            );
//        }
//        else {
//            return new apiResponseDTO(
//                    false,
//                    "Note not found",
//                    null
//            );
//        }
//    }
//
//    public apiResponseDTO deleteNote(int id){
//        if(repo.existsById(id)){
//            repo.deleteById(id);
//            return new apiResponseDTO(
//                    true,
//                    "Note successfully deleted",
//                    null
//            );
//        }else{
//            return new apiResponseDTO(
//                    false,
//                    "Note not found",
//                    null
//            );
//        }
//    }
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
