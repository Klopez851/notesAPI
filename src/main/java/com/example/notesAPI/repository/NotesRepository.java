package com.example.notesAPI.repository;

import com.example.notesAPI.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Note, Integer> {
}
