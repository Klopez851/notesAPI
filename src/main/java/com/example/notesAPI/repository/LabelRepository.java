package com.example.notesAPI.repository;

import com.example.notesAPI.dto.Label.LabelDTO;
import com.example.notesAPI.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LabelRepository extends JpaRepository<Label, Integer> {

//    //look into how to inject value into query
//    @Query("SELECT 'labelName' FROM label WHERE label.user_id = ?")
    Label findByLabelNameAndUser(String labelName, int userID);

    @Query("SELECT new com.example.notesAPI.dto.Label.LabelDTO(l.labelName, l.labelID) FROM Label l WHERE l.user.id = ?1")
    List<LabelDTO> findAllByUser(int userID);
}
