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
    private int noteID; //making this an int (not a long) since its not a big app, its for personal use

    private int userID;
    private int labelID;
    private String colorID;

    private String title;
    private String textContent;
    private boolean pinned;
    private boolean hidden;
    private String cosmetics;
    private boolean viewOnly;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    private boolean deleted;
    private LocalDateTime timeLeftBeforeDeletion;

    ///////////////////
    /// CONSTRUCTOR ///
    ///////////////////

    public Note(){}
    public Note(String title,String textContent){
        this.title = title;
        this.textContent = textContent;
    }
}
