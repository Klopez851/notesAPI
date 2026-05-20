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
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "label_id")
    private int labelID;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserTable user;

    private String labelName;

    public Label(UserTable user, String labelName) {
        this.user = user;
        this.labelName = labelName;
    }

    public String toString() {
        return (labelID + " " + user.getEmail() + " " + labelName);
    }

    public boolean equals(Label label) {
        if (this.labelID == label.getLabelID()
                && this.labelName.equalsIgnoreCase(label.getLabelName())
                && this.user.equals(label.getUser())) {
            return true;
        }
        return false;
    }


}