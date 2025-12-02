package com.example.notesAPI.repository;

import com.example.notesAPI.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface notesRepository extends JpaRepository<Note, Integer> {
}
