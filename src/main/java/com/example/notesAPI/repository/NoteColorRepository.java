package com.example.notesAPI.repository;

import com.example.notesAPI.model.NoteColor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteColorRepository extends JpaRepository<NoteColor, Integer> {

    NoteColor findByColorHEXAndUser(String colorHex, int userID);

    boolean existsByColorHEX(String colorHex);
}
