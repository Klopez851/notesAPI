package com.example.notesAPI.repository;

import com.example.notesAPI.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LabelRepository extends JpaRepository<Label, Integer> {

    //change this query to look for label name using the user id
    @Query("")
    Label findByLabelName(String labelName);
}
