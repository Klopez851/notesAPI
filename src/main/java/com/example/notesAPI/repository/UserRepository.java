package com.example.notesAPI.repository;

import com.example.notesAPI.model.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserTable, Integer> //table, pk type
{
     UserTable findByEmail(String email);

     boolean existsByEmail(String username);
}
