package com.example.notesAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class NoteColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int colorID;

    @ManyToOne
    @JoinColumn(name = "userID")
    private UserTable user;

    private String colorHEX;

    public NoteColor(){}

}
