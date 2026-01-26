package com.example.notesAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class userTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

    @ManyToOne
    @JoinColumn(name = "template_id", nullable = false)//nullable = false since every user must have a template
    private UITemplate uiTemplate;

    private String username;
    private String email;
    private String passwordHash;
    private LocalDateTime createdAt;

    public userTable(){}

    public String toString(){
        return (userID +" "+ username+" "+email+" "+passwordHash+" "+createdAt+".");
    }
}
