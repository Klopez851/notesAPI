package com.example.notesAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface notesRepository extends JpaRepository<Notes, Integer> {
}
