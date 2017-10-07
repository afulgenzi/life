package com.fulg.life.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fulg.life.model.entities.Memo;

public interface MemoRepository extends JpaRepository<Memo, Long> {
}
