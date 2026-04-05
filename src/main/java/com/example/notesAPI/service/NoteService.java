//package com.example.notesAPI.service;
//
//import com.example.notesAPI.dto.ApiResponseDTO;
//import com.example.notesAPI.dto.Note.CreateNoteDTO;
//import com.example.notesAPI.model.Label;
//import com.example.notesAPI.model.Note;
//import com.example.notesAPI.model.NoteColor;
//import com.example.notesAPI.model.UserTable;
//import com.example.notesAPI.repository.NotesRepository;
//import com.example.notesAPI.repository.UserRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//import java.util.Optional;
//
//@Service
//@AllArgsConstructor
//public class NoteService {
//
//    private final NotesRepository noteRepo;
//    private final UserRepository userRepo;
//
//    //change param type to createNoteDTO
//    public ApiResponseDTO<String> createNote(CreateNoteDTO note){
//        //clean data
//        String userEmail = note.getEmail().toLowerCase();
//        String noteTitle = note.hasTitle() ? note.getTitle().strip() : "";
//        String noteContent = note.hasContent() ? note.getContent().strip() : "";
//        Label noteLabel = note.hasLabel() ? note.getLabel(): null;
//        NoteColor noteColor = note.hasColor() ? note.getNoteColor() : null;
//
//        //get user
//        UserTable user = userRepo.findByEmail(userEmail);
//
//        //get label (need to write custom query)
//
//        //get color write custom query as well fuuuuuucccckkkkk
//
//        //should i write a trigger that if a label gets added that isnt present in the db it should automatically
//        // be created for the user, or should i handle that in the api? we live and we learn and wee will definately
//        // learn
//
//        //create note
//        Note note = new Note(noteTitle,noteContent,noteLabel);
//
//        //save the note
//        repo.save(note);
//        return new ApiResponseDTO<>(true, "Note successfully created", null);
//    }
//
//
////    // need to figure out how to look for strings, if via id, or if via title, or via text content, so the front end
////    public apiResponseDTO<noteResponseDTO> getNote(int id){
////        Optional<Note> note = repo.findById(id);
////        if(note.isPresent()){
////            return new apiResponseDTO<noteResponseDTO>(
////                    true,
////                    "Note found",
////                    noteResponseDTO.toDTO(note.get())
////            );
////        }
////        else{
////            return new apiResponseDTO<noteResponseDTO>(
////                    false,
////                    "Note not found",
////                    null
////            );
////        }
////    }
////
////    public apiResponseDTO<updateNoteDTO> updateNote(int id,Optional<String> content, Optional<String> title,
////                                                    Optional<String> label){
////        //get old note values
////        Optional<Note> tempNote = repo.findById(id);
////        if(tempNote.isPresent()) {
////            Note note = tempNote.get();
////
////            //update whatever needs to be updated;
////            if (title.isPresent()) {
////                note.setTitle(title.get());
////            }
////            if (content.isPresent()) {
////                note.setContent(content.get());
////            }
////            if (label.isPresent()) {
////                note.setLabel(label.get());
////            }
////
////            //save time of update
////            note.setUpdatedAt(LocalDateTime.now());
////
////            //save he updated note
////            repo.save(note);
////
////            //send api response
////            return new apiResponseDTO<updateNoteDTO>(
////                    true,
////                    "Note successfully updated",
////                    updateNoteDTO.toDTO(note)
////            );
////        }
////        else {
////            return new apiResponseDTO(
////                    false,
////                    "Note not found",
////                    null
////            );
////        }
////    }
////
////    public apiResponseDTO deleteNote(int id){
////        if(repo.existsById(id)){
////            repo.deleteById(id);
////            return new apiResponseDTO(
////                    true,
////                    "Note successfully deleted",
////                    null
////            );
////        }else{
////            return new apiResponseDTO(
////                    false,
////                    "Note not found",
////                    null
////            );
////        }
////    }
//
//
//}
