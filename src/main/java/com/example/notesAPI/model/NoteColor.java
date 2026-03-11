package com.example.notesAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "notecolor")
public class NoteColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id")
    private int colorID;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserTable user;

    @Column(name = "color_hex")
    private String colorHEX;

    public NoteColor(){}

}
