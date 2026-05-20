package com.example.notesAPI.repository;

import com.example.notesAPI.dto.Note.NoteDTO;
import com.example.notesAPI.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotesRepository extends JpaRepository<Note, Integer> {

    @Query("""
                SELECT new com.example.notesAPI.dto.Note.NoteDTO(
                    n.noteID,
                    n.title,
                    n.textContent,
                    n.label.labelName,
                    n.label.labelID,
                    n.color.colorHEX,
                    n.color.colorID,
                    n.cosmetics,
                    n.pinned,
                    n.hidden,
                    n.createdAt,
                    n.updatedAt,
                    n.deleted,
                    n.timeLeftBeforeDeletion
                )
                FROM Note n
                LEFT JOIN n.label l
                LEFT JOIN n.color c
                WHERE n.user.userID = ?1
            """)
    List<NoteDTO> getAllNoteByUser(int userID);

    @Query("""
                SELECT new com.example.notesAPI.dto.Note.NoteDTO(
                    n.noteID,
                    n.title,
                    n.textContent,
                    n.label.labelName,
                    n.label.labelID,
                    n.color.colorHEX,
                    n.color.colorID,
                    n.cosmetics,
                    n.pinned,
                    n.hidden,
                    n.createdAt,
                    n.updatedAt,
                    n.deleted,
                    n.timeLeftBeforeDeletion
                )
                FROM Note n
                LEFT JOIN n.label l
                LEFT JOIN n.color c
                WHERE n.noteID = ?1
            """)
    NoteDTO getNoteByUser(int noteID);
}
