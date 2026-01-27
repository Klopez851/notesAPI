package com.example.notesAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class UserTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UITemplate> uiTemplate;

    private String username;
    private String email;
    private String passwordHash;
    private LocalDateTime createdAt;

    public UserTable(){}

    public String toString(){
        return (userID +" "+ username+" "+email+" "+passwordHash+" "+createdAt+".");
    }
}
