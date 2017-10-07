package com.fulg.life.model.repository;

import java.util.Date;
import java.util.List;

import com.fulg.life.model.entities.Category;
import com.fulg.life.model.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.fulg.life.model.entities.BankAccountMovement;

public interface BankAccountMovementRepository extends JpaRepository<BankAccountMovement, Long>, QueryDslPredicateExecutor<BankAccountMovement> {

    @Query("SELECT movement FROM BankAccountMovement movement WHERE movement.bankAccount.pk = :accountPk AND month(movement.date) = :month AND year(movement.date) = :year")
    List<BankAccountMovement> findByMonthAndAccount(@Param("accountPk") Long accountPk, @Param("month") int month, @Param("year") int year);

    @Query("SELECT movement FROM BankAccountMovement movement WHERE month(movement.date) = :month AND year(movement.date) = :year")
    List<BankAccountMovement> findByMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT movement " +
            " FROM BankAccountMovement movement " +
            " WHERE movement.bankAccount.pk = :accountPk " +
            " AND movement.bankTransfer.fromBankAccount.pk = :fromAccountPk " +
            " AND movement.bankTransfer.toBankAccount.pk = :toAccountPk " +
            " AND movement.date between :startDate and :endDate ")
    List<BankAccountMovement> findByTransfer(@Param("accountPk") Long accountPk, @Param("fromAccountPk") Long fromAccountPk, @Param("toAccountPk") Long toAccountPk, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT movement " +
            " FROM BankAccountMovement movement " +
            " WHERE movement.bankAccount.pk = :accountPk " +
            " AND movement.description like '%' || :description || '%'")
    List<BankAccountMovement> findByDescription(@Param("accountPk") Long accountPk, @Param("description") String description);

    @Query("SELECT movement FROM BankAccountMovement movement " +
            " WHERE movement.bankAccount.pk = :accountPk " +
            " AND description like '%' || :description || '%'" +
            " AND date between :startDate and :endDate ")
    List<BankAccountMovement> findByDescription(@Param("accountPk") Long accountPk, @Param("description") String description, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT movement FROM BankAccountMovement movement " +
            " WHERE movement.bankAccount.pk = :accountPk " +
            " AND description like '%' || :description || '%'" +
            " AND date between :startDate and :endDate " +
            " AND eu = :inOut ")
    List<BankAccountMovement> findByDescriptionAndInOut(@Param("accountPk") Long accountPk, @Param("description") String description, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("inOut") String inOut);

    @Query(" SELECT sum(case when (mov.currency.code != mov.bankAccount.currency.code) then (mov.amount * mov.currency.amountInEuro) else mov.amount end)" +
			" FROM BankAccountMovement mov" + 
			" WHERE mov.bankAccount.pk = :bankAccountPk " +
			" AND mov.eu = :type " + 
			" AND mov.date < :date OR (mov.date = :date AND mov.pk < :movementPk))")
    Double findSumByTypeBeforeMovement(@Param("bankAccountPk") Long bankAccountPk, @Param("type") String type, @Param("date") Date date, @Param("movementPk") Long movementPk);
    
    @Query("SELECT movement " +
            " FROM BankAccountMovement movement " +
            " WHERE movement.bankAccount.pk = :bankAccountPk")
    List<BankAccountMovement> findByAccount(@Param("bankAccountPk") Long bankAccountPk);

    @Query("SELECT movement " +
            " FROM BankAccountMovement movement " +
            " WHERE movement.category.pk = :categoryPk ")
    List<BankAccountMovement> findByCategory(@Param("categoryPk") Long categoryPk);

    @Query("SELECT invoice.movements FROM Invoice invoice WHERE invoice.pk = :invoicePk")
    List<BankAccountMovement> findByInvoice(@Param("invoicePk") Long invoicePk);
}
