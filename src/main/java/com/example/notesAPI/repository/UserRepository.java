package com.example.notesAPI.repository;

import com.example.notesAPI.model.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserTable, Integer> //table, pk type
{
     Optional<UserTable> findByEmail(String email);

     boolean existsByEmail(String username);
}
