package com.example.notesAPI.repository;

import com.example.notesAPI.model.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<UserTable, Integer> //table, pk type
{
     //return type, name (jpa infers the query based on methos name), parameters
     UserTable findByUsername(String username);

     UserTable findByEmail(String email);

     boolean existsByEmail(String username);


}
