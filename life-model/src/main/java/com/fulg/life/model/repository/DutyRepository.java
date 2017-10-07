package com.fulg.life.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fulg.life.model.entities.Duty;

public interface DutyRepository extends JpaRepository<Duty, Long> {

    @Query("SELECT duty FROM Duty duty WHERE month(duty.date) = :month AND year(duty.date) = :year")
    List<Duty> findByMonth(@Param("month") int month, @Param("year") int year);
}
