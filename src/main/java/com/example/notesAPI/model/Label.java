package com.example.notesAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int labelID;

    @ManyToOne
    @JoinColumn(name = "userID")
    private userTable user;

    private String labelName;

    public Label() {
    }


}