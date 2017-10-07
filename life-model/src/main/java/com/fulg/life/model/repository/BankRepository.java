package com.fulg.life.model.repository;

import com.fulg.life.model.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BankRepository extends JpaRepository<Bank, Long> {

    @Query("SELECT b FROM Bank b WHERE code = :code")
    Bank findByCode(@Param("code") String code);
}
