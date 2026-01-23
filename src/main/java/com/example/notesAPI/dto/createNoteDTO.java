//package com.example.notesAPI.dto;
//
//import com.example.notesAPI.model.Note;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//
//@Data
//@AllArgsConstructor
//public class createNoteDTO {
//    /////////////////
//    /// VARIABLES ///
//    /////////////////
//    private int id;
//    private String title;
//    private String content;
//    private String label;
//
//    ///////////////////
//    /// CONSTRUCTOR ///
//    ///////////////////
//    public createNoteDTO(){}
//
//    public static createNoteDTO toDTO(Note note){
//        createNoteDTO dto = new createNoteDTO(
//
//            note.getTitle(),
//            note.getContent(),
//            note.getLabel()
//        );
//        return dto;
//    }
//}
