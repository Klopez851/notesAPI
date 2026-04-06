package com.example.notesAPI.repository;

import com.example.notesAPI.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface LabelRepository extends JpaRepository<Label, Integer> {

//    //look into how to inject value into query
//    @Query("SELECT 'labelName' FROM label WHERE label.user_id = ?")
    Label findByLabelNameAndUser(String labelName, int userID);
}
