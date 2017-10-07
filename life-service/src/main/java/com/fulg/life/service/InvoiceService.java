package com.fulg.life.service;

import com.fulg.life.model.entities.BankAccountMovement;
import com.fulg.life.model.entities.Invoice;

import java.util.List;

/**
 * Created by alessandro.fulgenzi on 28/07/16.
 */
public interface InvoiceService extends ItemService {

    List<Invoice> getByMovement(Long movementPk);

    void delete(Invoice invoice);

    Invoice save(Invoice invoice);

    Invoice addOrUpdateMovement(Invoice invoice, List<BankAccountMovement> movements, BankAccountMovement movement);
}
