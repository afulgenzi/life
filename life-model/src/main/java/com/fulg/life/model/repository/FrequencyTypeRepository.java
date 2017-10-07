package com.fulg.life.model.repository;

import com.fulg.life.model.entities.FrequencyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by alessandro.fulgenzi on 13/07/16.
 */
public interface FrequencyTypeRepository extends JpaRepository<FrequencyType, Long> {

    @Query("SELECT b FROM FrequencyType b WHERE code = :code")
    FrequencyType findByCode(@Param("code") String code);
}
