package com.example.notesAPI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class UITemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int templateID;

    private String templateName;
    private String templateDetails;

    public UITemplate(){}
}
