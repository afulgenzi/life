package com.fulg.life.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fulg.life.model.entities.GenericItem;
import com.fulg.life.model.entities.MigrationItem;

public interface MigrationItemRepository extends JpaRepository<GenericItem, Long> {

    @Query("SELECT item FROM MigrationItem item WHERE item.source = :source")
    List<MigrationItem> findBySource(@Param("source") String source);
}
