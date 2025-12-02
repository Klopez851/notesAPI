package com.example.notesAPI.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
public class Note {
    ///////////////////////
    /// CLASS VARIABLES ///
    ///////////////////////
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //making this an int (not a long) since its not a big app, its for personal use

    private String title;
    private String content;
    private String label;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    ///////////////////
    /// CONSTRUCTOR ///
    ///////////////////

    public Note(){}
    public Note(String title,String content,String label){
        this.title = title;
        this.content = content;
        this.label=label;
    }
}
