package com.fulg.life.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fulg.life.model.entities.MasterItem;

public interface MasterItemRepository extends JpaRepository<MasterItem, Long> {
}
