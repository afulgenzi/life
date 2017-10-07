package com.fulg.life.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fulg.life.model.entities.BankAccountOwner;

public interface BankAccountOwnerRepository extends JpaRepository<BankAccountOwner, Long> {
    @Query("SELECT owner FROM BankAccountOwner owner WHERE owner.bankAccount.pk = :bankAccountPk")
    List<BankAccountOwner> findByBankAccount(@Param("bankAccountPk") Long bankAccountPk);

    @Query("SELECT owner FROM BankAccountOwner owner WHERE owner.user.username = :username")
    List<BankAccountOwner> findByUserName(@Param("username") String username);

    @Query("SELECT owner FROM BankAccountOwner owner WHERE owner.user.username = :username AND owner.bankAccount.pk = :bankAccountPk")
    BankAccountOwner findByBankAccountAndUser(@Param("bankAccountPk") Long bankAccountPk, @Param("username") String username);
}
