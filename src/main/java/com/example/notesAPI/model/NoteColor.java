package com.example.notesAPI.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    public NoteColor(String colorHEX, UserTable user) {
        this.user = user;
        this.colorHEX = colorHEX;
    }

    public boolean equals(NoteColor color) {
        if (this.colorID == color.getColorID()
                && this.colorHEX.equalsIgnoreCase(color.getColorHEX())
                && this.user.equals(color.getUser())) {
            return true;
        }
        return false;
    }

}
