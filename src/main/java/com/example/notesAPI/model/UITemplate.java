package com.example.notesAPI.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UITemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int templateID;

    private String name;
    private String templateDetails;

    public UITemplate(){}
}
