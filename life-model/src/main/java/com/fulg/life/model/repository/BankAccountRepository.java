package com.fulg.life.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fulg.life.model.entities.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    @Query("SELECT account FROM BankAccount account WHERE isdefault = true AND account.user.username = :username")
    BankAccount findDefaultAccount(@Param("username") String username);

    @Query("SELECT account FROM BankAccount account WHERE accountnumber = :accountNumber AND bankname = :bankName")
    BankAccount findByBankAndAccountCode(@Param("accountNumber") String accountNumber, @Param("bankName") String bankName);

}
