package com.example.notesAPI.service;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.EmailDTO;
import com.example.notesAPI.dto.Label.UpdateBooleanStatusDTO;
import com.example.notesAPI.dto.Note.*;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NoteService {

    private final NotesRepository noteRepo;
    private final NoteColorRepository noteColorRepo;
    private final UserRepository userRepo;
    private final LabelRepository labelRepo;
    private final JWTService jwtService;

    /// /////////////////
    /// POST METHODS ///
    /// /////////////////

    public ApiResponseDTO<String> createNote(CreateNoteDTO noteDTO, HttpServletRequest request) {
        //clean the data
        String email = noteDTO.getEmail().strip().toLowerCase();
        String title = noteDTO.getTitle().orElse(null).strip();
        String content = noteDTO.getContent().orElse(null).strip();
        ;

        // validate the request
        if (isRequestValid(email, request)) {
            Optional<Label> label = null;
            Optional<NoteColor> color = null;

            //look up user
            Optional<UserTable> user = userRepo.findByEmail(email);
            if (user.isEmpty()) {
                throw new ResourceNotFoundException("A user associated with that email could not be found");
            }

            //look up label if not null
            if (noteDTO.getLabelID().isPresent()) {
                label = labelRepo.findById(noteDTO.getLabelID().get());
            }

            //look up color if not null
            if (noteDTO.getNoteColorID().isPresent()) {
                color = noteColorRepo.findById(noteDTO.getNoteColorID().get());
            }

            //create note
            Note note = new Note(user.get(), title, content, label.orElse(null), color.orElse(null));

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

        }
        throw new ForbiddenRequestException("Access denied: You can only modify your own account.");
    }

    /// ////////////////
    /// GET METHODS ///
    /// ////////////////

    public ApiResponseDTO<List<NoteDTO>> getNotes(EmailDTO emailDTO, HttpServletRequest request) {
        //clean data
        String email = emailDTO.getEmail().strip().toLowerCase();

        //validate request
        if (isRequestValid(email, request)) {
            //ensure user exists
            Optional<UserTable> user = userRepo.findByEmail(email);

            //fetch all notes
            List<NoteDTO> notes = noteRepo.findAllByUser(user.get().getUserID());

            //return response
            return new ApiResponseDTO<>(true, "Notes successfully fetched", notes);
        }
        throw new ForbiddenRequestException("Access denied: You can only modify your own account.");
    }

    public ApiResponseDTO<NoteDTO> getNote(GetNoteDTO noteDTO, HttpServletRequest request) {
        //clean data
        String email = noteDTO.getEmail().strip().toLowerCase();
        int noteID = noteDTO.getNoteID();

        //validate request
        if (isRequestValid(email, request)) {
            //ensure email exists
            Optional<UserTable> user = userRepo.findByEmail(email);

            //ensure note exists
            Optional<Note> reqNote = noteRepo.findById(noteID);

            // send note to front if user exists, notes exists, and the note is associated to the given user
            if (reqNote.isPresent()) {
                if (user.isPresent()) {
                    if (user.get().getUserID() == reqNote.get().getUser().getUserID()) {
                        NoteDTO note = noteRepo.findByIdAndConvertToDTO(noteID);
                        return new ApiResponseDTO<>(true, "note successfully fetched", note);

                    }
                    throw new ResourceNotFoundException("A note by that id associated with the provided user could not be found");
                }
                throw new ResourceNotFoundException("A user associated with the provided email could not be found");
            }
            throw new ResourceNotFoundException("A note associated with that id could not be found");
        }
        throw new ForbiddenRequestException("Access denied: You can only modify your own account.");
    }

    ///////////////////
    /// PUT METHODS ///
    ///////////////////

//    public ApiResponseDTO<String> updateNote(UpdateNoteDTO noteDTO, HttpServletRequest request) {
//        //clean data
//        String title = noteDTO.getTitle().strip();
//        String textContent = noteDTO.getTextContent().strip();
//        String cosmetics = noteDTO.getCosmetics().strip();
//        String email = noteDTO.getEmail().strip().toLowerCase();
//
//        //validate request
//        if(isRequestValid(email, request)){
//            //make sure user exists
//            Optional<UserTable> user = userRepo.findByEmail(email);
//
//            //make sure note exists
//            Optional<Note> note = noteRepo.findById(noteDTO.getId());
//
//            //get label and color if they exists
//            Optional<Label> label = labelRepo.findById(noteDTO.getLabel().getLabelID());
//            Optional<NoteColor> color = noteColorRepo.findById(noteDTO.getNoteColor().getColorID());
//
//            //make sure note is associated with the user
//            if(note.isPresent()){
//                if(user.isPresent()){
//                    if(user.get().getUserID() == note.get().getUser().getUserID()){
//                        //update whatever fields need to be updated
//                        if(!note.get().getTitle().equals(noteDTO.getTitle())){
//                            note.get().setTitle(noteDTO.getTitle());
//                        }
//
//                        if(!note.get().getTextContent().equals(noteDTO.getTextContent())){
//                            note.get().setTextContent(noteDTO.getTextContent());
//                        }
//
//                        if(!note.get().getLabel().equals(label.get())){
//                            note.get().setLabel(label.get());
//                        }
//
//                        if(!note.get().getColor().equals(color.get())){
//                            note.get().setColor(color.get());
//                        }
//
//                        if(!note.get().getTitle().equals(noteDTO.getTitle())){
//                            note.get().setTitle(noteDTO.getTitle());
//                        }
//
//                        if(!note.get().getCosmetics().equals(noteDTO.getCosmetics())){
//                            note.get().setCosmetics(noteDTO.getCosmetics());
//                        }
//
//                        if(note.get().isPinned() != noteDTO.isPinned()){
//                            note.get().setPinned(noteDTO.isPinned());
//                        }
//
//                        if(note.get().isHidden() != noteDTO.isHidden()){
//                            note.get().setHidden(noteDTO.isHidden());
//                        }
//
//                        if(note.get().isDeleted() != noteDTO.isDeleted()){
//                            note.get().setDeleted(noteDTO.isDeleted());
//                        }
//
//                        note.get().setUpdatedAt(LocalDateTime.now());
//
//                        //save entity
//                        noteRepo.save(note.get());
//
//                        return new ApiResponseDTO<String>(true,"note succesfully updated", noteRepo.findById(noteDTO.getId()).get().toString());
//
//                    }
//                }
//            }
//
//        }throw new ForbiddenRequestException("Access denied: You can only modify your own account.");
//    }

    /////////////////////
    /// PATCH METHODS ///
    /////////////////////

    public ApiResponseDTO<String> updatePinned(UpdateBooleanStatusDTO noteDTO, HttpServletRequest request) {
        //clean data
        String email = noteDTO.getEmail().strip().toLowerCase();

        //validate request
        if(isRequestValid(email, request)){
            Optional<Note> note = noteRepo.findById(noteDTO.getNoteID());
            Optional<UserTable> user = userRepo.findByEmail(email);

            if(note.isPresent()){
                if(user.isPresent()){
                    if(user.get().getUserID() == note.get().getUser().getUserID()){
                        //update pinned status
                        note.get().setPinned(noteDTO.isNewValue());

                        //update updatedAt field
                        note.get().setUpdatedAt(LocalDateTime.now());

                        //save entity
                        noteRepo.save(note.get());

                        return new ApiResponseDTO<String>(true, "Note sucessfully updated", null);

                    }else{throw new ResourceNotFoundException("A note with that id associated with the provided user could not be found");}
                }else{throw new ResourceNotFoundException("A user associated with that email could not be found");}
            }else{throw new ResourceNotFoundException("A note associated with that id could not be found");}
        }throw new ForbiddenRequestException("Access denied: You can only modify your own account.");
    }

    public ApiResponseDTO<String> updateHidden(UpdateBooleanStatusDTO noteDTO, HttpServletRequest request) {
        //clean data
        String email = noteDTO.getEmail().strip().toLowerCase();

        //validate request
        if(isRequestValid(email, request)){
            Optional<Note> note = noteRepo.findById(noteDTO.getNoteID());
            Optional<UserTable> user = userRepo.findByEmail(email);

            if(note.isPresent()){
                if(user.isPresent()){
                    if(user.get().getUserID() == note.get().getUser().getUserID()){
                        //update hidden status
                        note.get().setHidden(noteDTO.isNewValue());

                        //update updatedAt field
                        note.get().setUpdatedAt(LocalDateTime.now());

                        //save entity
                        noteRepo.save(note.get());

                        return new ApiResponseDTO<String>(true, "Note sucessfully updated", null);

                    }else{throw new ResourceNotFoundException("A note with that id associated with the provided user could not be found");}
                }else{throw new ResourceNotFoundException("A user associated with that email could not be found");}
            }else{throw new ResourceNotFoundException("A note associated with that id could not be found");}
        }throw new ForbiddenRequestException("Access denied: You can only modify your own account.");
    }

    public ApiResponseDTO<String> updateDeleted(UpdateBooleanStatusDTO noteDTO, HttpServletRequest request) {
        //clean data
        String email = noteDTO.getEmail().strip().toLowerCase();

        //validate request
        if(isRequestValid(email, request)){
            Optional<Note> note = noteRepo.findById(noteDTO.getNoteID());
            Optional<UserTable> user = userRepo.findByEmail(email);

            if(note.isPresent()){
                if(user.isPresent()){
                    if(user.get().getUserID() == note.get().getUser().getUserID()){
                        //update deleted status
                        note.get().setDeleted(noteDTO.isNewValue());

                        //update updatedAt field
                        note.get().setUpdatedAt(LocalDateTime.now());

                        //save entity
                        noteRepo.save(note.get());

                        return new ApiResponseDTO<String>(true, "Note sucessfully updated", null);

                    }else{throw new ResourceNotFoundException("A note with that id associated with the provided user could not be found");}
                }else{throw new ResourceNotFoundException("A user associated with that email could not be found");}
            }else{throw new ResourceNotFoundException("A note associated with that id could not be found");}
        }throw new ForbiddenRequestException("Access denied: You can only modify your own account.");
    }

    public ApiResponseDTO<String> updateViewOnly(UpdateBooleanStatusDTO noteDTO, HttpServletRequest request) {
        //clean data
        String email = noteDTO.getEmail().strip().toLowerCase();

        //validate request
        if(isRequestValid(email, request)){
            Optional<Note> note = noteRepo.findById(noteDTO.getNoteID());
            Optional<UserTable> user = userRepo.findByEmail(email);

            if(note.isPresent()){
                if(user.isPresent()){
                    if(user.get().getUserID() == note.get().getUser().getUserID()){
                        //update viewonly status
                        note.get().setViewOnly(noteDTO.isNewValue());

                        //update updatedAt field
                        note.get().setUpdatedAt(LocalDateTime.now());

                        //save entity
                        noteRepo.save(note.get());

                        return new ApiResponseDTO<String>(true, "Note sucessfully updated", null);

                    }else{throw new ResourceNotFoundException("A note with that id associated with the provided user could not be found");}
                }else{throw new ResourceNotFoundException("A user associated with that email could not be found");}
            }else{throw new ResourceNotFoundException("A note associated with that id could not be found");}
        }throw new ForbiddenRequestException("Access denied: You can only modify your own account.");
    }

    public ApiResponseDTO<String> updateCosmetics(UpdateCosmeticDTO cosmeticsDTO, HttpServletRequest request) {
        //clean data
        String email = cosmeticsDTO.getEmail().strip().toLowerCase();

        //validate request
        if(isRequestValid(email, request)){
            Optional<Note> note = noteRepo.findById(cosmeticsDTO.getNoteID());
            Optional<UserTable> user = userRepo.findByEmail(email);

            if(note.isPresent()){
                if(user.isPresent()){
                    if(user.get().getUserID() == note.get().getUser().getUserID()){
                        //update cosmetics
                        note.get().setCosmetics(cosmeticsDTO.getCosmetics());

                        //update updatedAt field
                        note.get().setUpdatedAt(LocalDateTime.now());

                        //save entity
                        noteRepo.save(note.get());

                        return new ApiResponseDTO<String>(true, "Note sucessfully updated", null);

                    }else{throw new ResourceNotFoundException("A note with that id associated with the provided user could not be found");}
                }else{throw new ResourceNotFoundException("A user associated with that email could not be found");}
            }else{throw new ResourceNotFoundException("A note associated with that id could not be found");}
        }throw new ForbiddenRequestException("Access denied: You can only modify your own account.");
    }

    public ApiResponseDTO<String> updateLabel(UpdateNoteLabelDTO labelDTO, HttpServletRequest request) {
        //clean data
        String email = labelDTO.getEmail().strip().toLowerCase();

        //validate request
        if(isRequestValid(email, request)){
            Optional<Note> note = noteRepo.findById(labelDTO.getNoteID());
            Optional<UserTable> user = userRepo.findByEmail(email);
            Optional<Label> label = labelRepo.findById(labelDTO.getLabelID());

            //ensure all parts needed for the update exists
            if(label.isPresent()) {
                if (note.isPresent()) {
                    if (user.isPresent()) {
                        if (user.get().getUserID() == note.get().getUser().getUserID()) {
                            //update label
                            note.get().setLabel(label.get());

                            //update updatedAt field
                            note.get().setUpdatedAt(LocalDateTime.now());

                            //save entity
                            noteRepo.save(note.get());

                            return new ApiResponseDTO<String>(true, "Note sucessfully updated", null);

                        } else {throw new ResourceNotFoundException("A note with that id associated with the provided user could not be found");}
                    } else {throw new ResourceNotFoundException("A user associated with that email could not be found");}
                } else {throw new ResourceNotFoundException("A note associated with that id could not be found");}
            }else {throw new ResourceNotFoundException("A label associated with that id could not be found");}
        }throw new ForbiddenRequestException("Access denied: You can only modify your own account.");
    }

    public ApiResponseDTO<String> updateNoteColor(UpdateColorDTO colorDTO, HttpServletRequest request) {
        //clean data
        String email = colorDTO.getEmail().strip().toLowerCase();

        //validate request
        if(isRequestValid(email, request)){
            Optional<Note> note = noteRepo.findById(colorDTO.getNoteID());
            Optional<UserTable> user = userRepo.findByEmail(email);
            Optional<NoteColor> color = noteColorRepo.findById(colorDTO.getColorID());

            //ensure all parts needed for the update exists
            if(color.isPresent()) {
                if (note.isPresent()) {
                    if (user.isPresent()) {
                        if (user.get().getUserID() == note.get().getUser().getUserID()) {
                            //update noteColor
                            note.get().setColor(color.get());

                            //update updatedAt field
                            note.get().setUpdatedAt(LocalDateTime.now());

                            //save entity
                            noteRepo.save(note.get());

                            return new ApiResponseDTO<String>(true, "Note sucessfully updated", null);

                        } else {throw new ResourceNotFoundException("A note with that id associated with the provided user could not be found");}
                    } else {throw new ResourceNotFoundException("A user associated with that email could not be found");}
                } else {throw new ResourceNotFoundException("A note associated with that id could not be found");}
            }else {throw new ResourceNotFoundException("A NoteColor associated with that id could not be found");}
        }throw new ForbiddenRequestException("Access denied: You can only modify your own account.");
    }

    //////////////////////
    /// DELETE METHODS ///
    //////////////////////

    /// ////////////////////
    /// PRIVATE METHODS ///
    /// ////////////////////

    private boolean isRequestValid(String userEmail, HttpServletRequest request) {
        String token;
        String JWTemail = null;

        //get auth header from request
        String authHeader = request.getHeader("Authorization");

        //ensure header isn't empty or wrongly formatted
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            //extract token and get email from token
            token = authHeader.substring(7);//jwt string starts at 7th index of header string
            JWTemail = jwtService.extractEmail(token);
            //dont need to verify token validity bc jwt filter takes care of that for all incoming requests.
        }

        //ensure emails match
        if (userEmail.equals(JWTemail)) {
            return true;
        }

        return false;
    }

}
