package com.fulg.life.service;

import com.fulg.life.model.entities.ImportBankMovements;
import com.fulg.life.model.entities.User;

public interface ImportBankMovementsService extends ItemService {
    ImportBankMovements getByMonth(Long bankAccountPk, int year, int month, User user);

    ImportBankMovements insert(ImportBankMovements item);

    void delete(Long bankAccountPk, int year, int month, User user);
}
