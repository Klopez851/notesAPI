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

    private int userID;

    private String colorHEX;

    public NoteColor(){}

}
