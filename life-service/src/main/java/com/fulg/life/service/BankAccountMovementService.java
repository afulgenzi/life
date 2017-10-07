package com.fulg.life.service;

import java.util.Date;
import java.util.List;

import com.fulg.life.model.entities.BankAccountMovement;
import com.fulg.life.model.entities.Category;
import com.fulg.life.model.entities.User;

public interface BankAccountMovementService extends ItemService {

    List<BankAccountMovement> getAll(Long bankAccount, User user);

    List<BankAccountMovement> getByMonth(Long bankAccountPk, int year, int month, User user);

    List<BankAccountMovement> getByMonth(int year, int month, User user);

    List<BankAccountMovement> getByTransfer(String fromTo, Long fromAccountPk, Long toAccountPk, Date actualStartDate,
                                            Date actualEndDate, User user);

    List<BankAccountMovement> getByAttributes(Long bankAccountPk, String description, Long categoryPk, Date startDate,
                                              Date endDate, String inOut, boolean uncategorised, User user);

    List<BankAccountMovement> getByDescription(Long bankAccountPk, String description, Long categoryPk, Date startDate,
                                               Date endDate, String inOut, User user);

    BankAccountMovement update(BankAccountMovement eu);

    BankAccountMovement insert(BankAccountMovement eu);

    void delete(BankAccountMovement eu);

    BankAccountMovement getByIdEU(Long key);

    Double getBalanceBeforeMovement(Long bankAccountPk, Date date, Long movementPk);

    Double getOverallBalanceBeforeMovement(User user, Date date, Long movementPk);

    BankAccountMovement getLatestByCategory(Category category, User user);

    List<BankAccountMovement> getByCategory(final Long categoryPk, User user);

    List<BankAccountMovement> getByInvoice(final Long invoicePk, User user);
}
