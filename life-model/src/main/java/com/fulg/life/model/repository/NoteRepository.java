package com.fulg.life.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fulg.life.model.entities.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

}
