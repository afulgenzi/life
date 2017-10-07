package com.fulg.life.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fulg.life.model.entities.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    @Query("SELECT currency FROM Currency currency WHERE code = :code")
    Currency findByCode(@Param("code") String code);
}
