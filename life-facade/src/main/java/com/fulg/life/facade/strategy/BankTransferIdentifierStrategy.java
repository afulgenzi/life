package com.fulg.life.facade.strategy;

import com.fulg.life.model.entities.BankAccount;
import com.fulg.life.model.entities.User;

public interface BankTransferIdentifierStrategy {
	BankAccount getBankAccountForBankTransfer(User user, String bankCode, String movementDescription, String fromBankAccount);
}
