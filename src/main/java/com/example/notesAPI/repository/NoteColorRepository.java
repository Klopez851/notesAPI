package com.example.notesAPI.repository;

import com.example.notesAPI.dto.noteColor.NoteColorDTO;
import com.example.notesAPI.model.NoteColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoteColorRepository extends JpaRepository<NoteColor, Integer> {

    boolean existsByColorHEX(String colorHex);

    @Query("SELECT new com.example.notesAPI.dto.noteColor.NoteColorDTO(n.colorHEX, n.colorID) " +
            "FROM NoteColor n " +
            "WHERE n.user.id = ?1")
    List<NoteColorDTO> findAllByUser(int userID);
}
