package com.example.notesAPI.service;

import com.example.notesAPI.repository.NoteColorRepository;
import com.example.notesAPI.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NoteColorService {

    private final UserRepository userRepo;
    private final NoteColorRepository noteColorRepo;
}
