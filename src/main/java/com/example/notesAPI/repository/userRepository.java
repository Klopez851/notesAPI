package com.example.notesAPI.repository;

import com.example.notesAPI.model.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

public interface userRepository extends JpaRepository<UserTable, Integer> //table, pk type
{
     UserTable findByUsername(String username);
}
