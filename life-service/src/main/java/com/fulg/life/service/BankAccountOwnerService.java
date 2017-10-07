package com.fulg.life.service;

import com.fulg.life.model.entities.BankAccount;
import com.fulg.life.model.entities.User;

public interface BankAccountOwnerService {
	public Boolean canWrite(BankAccount bankAccount, User user);
}
