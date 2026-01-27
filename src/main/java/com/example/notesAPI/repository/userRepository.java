package com.example.notesAPI.repository;

import com.example.notesAPI.model.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<UserTable, Integer> {
}
