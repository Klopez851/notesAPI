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
    @Column(name = "user_id")
    private int userID;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UITemplate> uiTemplate;
              //refers to the field name in the child entity (Java class)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<NoteColor> noteColor;

    private String username;
    private String email;

    @Column(name = "user_password")
    private String userPassword;
    private LocalDateTime createdAt;

    public UserTable(){}

    public String toString(){
        return (userID +" "+ username+" "+email+" "+ userPassword +" "+createdAt+".");
    }
}
