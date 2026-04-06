package com.example.notesAPI.service;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.Note.CreateNoteDTO;
import com.example.notesAPI.model.Label;
import com.example.notesAPI.model.Note;
import com.example.notesAPI.model.NoteColor;
import com.example.notesAPI.model.UserTable;
import com.example.notesAPI.repository.LabelRepository;
import com.example.notesAPI.repository.NoteColorRepository;
import com.example.notesAPI.repository.NotesRepository;
import com.example.notesAPI.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NoteService {

    private final NotesRepository noteRepo;
    private final NoteColorRepository noteColorRepo;
    private final UserRepository userRepo;
    private final LabelRepository labelRepo;

    //change param type to createNoteDTO
    public ApiResponseDTO<String> createNote(CreateNoteDTO noteDTO){
        Label label = null;
        NoteColor color = null;

        //clean data
        String userEmail = noteDTO.getEmail().toLowerCase();
        String noteTitle = noteDTO.hasTitle() ? noteDTO.getTitle().strip() : "";
        String noteContent = noteDTO.hasContent() ? noteDTO.getContent().strip() : "";
        String noteLabel = noteDTO.hasLabel() ? noteDTO.getLabel(): "";
        String noteColor = noteDTO.hasColor() ? noteDTO.getNoteColor() : "";

        //get user
        UserTable user = userRepo.findByEmail(userEmail);

        //get label
        if(!(noteLabel.isBlank())){
            label = labelRepo.findByLabelNameAndUser(noteLabel, user.getUserID());

            //if label does exist, create it then fetch it
            if(label == null){
                labelRepo.save(new Label(user, noteLabel));
                label = labelRepo.findByLabelNameAndUser(noteLabel, user.getUserID());
            }
        }
        //get color
        if(!(noteColor.isBlank())){
            color = noteColorRepo.findByColorHEXAndUser(noteColor, user.getUserID());

            //if color doesnt exist, create it and the fetch it
            if(color == null){
                noteColorRepo.save(new NoteColor(noteColor, user));
                color = noteColorRepo.findByColorHEXAndUser(noteColor, user.getUserID());
            }
        }

        //create note
        Note note = new Note(user,noteTitle,noteContent,label, color);

        //save the note
        noteRepo.save(note);

        return new ApiResponseDTO<>(true, "Note successfully created", note.toString());
    }


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
//                                                    Optional<String> label){
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
//            if (label.isPresent()) {
//                note.setLabel(label.get());
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


}
