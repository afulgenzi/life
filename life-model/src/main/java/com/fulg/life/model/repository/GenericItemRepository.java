package com.fulg.life.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fulg.life.model.entities.GenericItem;

public interface GenericItemRepository extends JpaRepository<GenericItem, Long> {

}
