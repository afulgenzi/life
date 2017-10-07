package com.fulg.life.facade;

import com.fulg.life.data.BankAccountData;
import com.fulg.life.model.entities.User;

import java.util.List;

public interface BankAccountFacade {

	List<BankAccountData> getAll(User user);

	BankAccountData getById(Long id, User user);

	BankAccountData update(BankAccountData item);

	BankAccountData insert(BankAccountData item);

	void delete(BankAccountData item);
}
