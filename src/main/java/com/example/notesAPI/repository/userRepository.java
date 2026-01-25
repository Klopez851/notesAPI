package com.example.notesAPI.repository;

import com.example.notesAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<User, Integer> {
}
