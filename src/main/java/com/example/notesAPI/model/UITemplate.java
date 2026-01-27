package com.example.notesAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UITemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int templateID;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private UserTable user;

    private String templateName;
    private String templateDetails;

    public UITemplate(){}

    public String toString(){
        if(user !=null){
            return (user.getUserID() +" "+templateName+" "+templateDetails);
        }
        return (null+" "+templateName+" "+templateDetails);
    }
}
