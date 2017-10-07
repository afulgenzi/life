package com.fulg.life.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fulg.life.model.entities.BankTransfer;

public interface BankTransferRepository extends JpaRepository<BankTransfer, Long> {
    @Query("SELECT transfer FROM BankTransfer transfer WHERE transfer.fromMovement.pk = :bankAccountMovementPk OR transfer.toMovement.pk = :bankAccountMovementPk")
    BankTransfer findByBankAccountMovement(@Param("bankAccountMovementPk") Long bankAccountMovementPk);
}
