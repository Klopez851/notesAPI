package com.example.notesAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    public NoteColor(String colorHEX, UserTable user){
        this.user = user;
        this.colorHEX = colorHEX;
    }

}
