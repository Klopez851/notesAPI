package com.example.notesAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(name = "userID")
    private UserTable user;

    @ManyToOne
    @JoinColumn(name = "labelName")
    private Label label;

    @ManyToOne
    @JoinColumn(name = "colorID")
    private NoteColor color;

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
