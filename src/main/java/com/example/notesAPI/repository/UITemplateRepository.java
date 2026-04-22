package com.example.notesAPI.repository;

import com.example.notesAPI.dto.UITemplate.GetTemplateDTO;
import com.example.notesAPI.model.UITemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UITemplateRepository extends JpaRepository<UITemplate, Integer> {

    @Query("SELECT new com.example.notesAPI.dto.UITemplate.GetTemplateDTO(t.templateID, t.templateName, t.templateDetails) " +
            "FROM UITemplate t WHERE t.user.id = ?1")
    List<GetTemplateDTO> findAllByUser(int userID);
}
