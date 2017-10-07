package com.fulg.life.model.repository;

import com.fulg.life.model.entities.ImportBankMovements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImportBankMovementsRepository extends JpaRepository<ImportBankMovements, Long> {

    @Query("SELECT importMov FROM ImportBankMovements importMov WHERE importMov.bankAccount.pk = :bankAccountPk AND importMov.month = :month AND importMov.year = :year")
    ImportBankMovements findByMonth(@Param("bankAccountPk") Long bankAccountPk, @Param("month") int month, @Param("year") int year);
}
