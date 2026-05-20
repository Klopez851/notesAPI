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
public class UITemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id")
    private int templateID;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private UserTable user;

    private String templateName;
    private String templateDetails;

    public String toString() {
        if (user != null) {
            return (user.getUserID() + " " + templateName + " " + templateDetails);
        }
        return (null + " " + templateName + " " + templateDetails);
    }
}
