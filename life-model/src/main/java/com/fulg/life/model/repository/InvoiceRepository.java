package com.fulg.life.model.repository;

import com.fulg.life.model.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by alessandro.fulgenzi on 26/07/16.
 */
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query("SELECT movement.invoices FROM BankAccountMovement movement WHERE movement.pk = :movementPk")
    List<Invoice> findByMovement(@Param("movementPk") Long movementPk);

}
