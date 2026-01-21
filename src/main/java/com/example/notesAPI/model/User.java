package com.example.notesAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

    @ManyToOne
    @JoinColumn(name = "TemplateID", nullable = false)//nullable = false since every user must have a template
    private UITemplate uiTemplate;

    private String username;
    private String passwordHash;
    private LocalDateTime createdAt;

    public User(){}

}
