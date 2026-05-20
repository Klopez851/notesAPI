package com.example.notesAPI.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Label> label;

    private String username;
    private String email;

    @Column(name = "user_password")
    private String userPassword;
    private LocalDateTime createdAt;


    public String toString() {
        return (userID + " " + username + " " + email + " " + userPassword + " " + createdAt + ".");
    }

    public boolean equals(UserTable user) {

        if (this.userID == user.getUserID()
                && this.username == user.getUsername()
                && this.email == user.getEmail()
                && this.userPassword.equals(user.getUserPassword())) {
            return true;
        }
        return false;
    }
}
