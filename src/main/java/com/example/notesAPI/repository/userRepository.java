package com.example.notesAPI.repository;

import com.example.notesAPI.model.userTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<userTable, Integer> {
}
