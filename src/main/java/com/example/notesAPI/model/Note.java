package com.example.notesAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Note {
    ///////////////////////
    /// CLASS VARIABLES ///
    ///////////////////////
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private int noteID; //making this an int (not a long) since its not a big app, its for personal use

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserTable user;

    @ManyToOne
    @JoinColumn(name = "label_id")//name of fk in note that refers to the label table
    private Label label;

    @ManyToOne
    @JoinColumn(name = "color_id")
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

    public Note(UserTable user,String title,String textContent,Label label, NoteColor noteColor){
        this.title = title;
        this.textContent = textContent;
        this.label=label;
        this.user=user;
        this.color = noteColor;
    }

    public String toString(){
        return (noteID+" "+user.getEmail()+" "+title+" "+textContent);
    }
}
