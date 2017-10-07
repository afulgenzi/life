package com.fulg.life.service;

import java.util.List;

import com.fulg.life.model.entities.BankAccount;
import com.fulg.life.model.entities.User;

public interface BankAccountService extends ItemService {

	public List<BankAccount> getAll(User user);

	public BankAccount getByPk(Long ok);

	public BankAccount getIntesa1();

	public BankAccount getIntesa2();

	public BankAccount getBarclays1();

	public BankAccount getBarclays2();

	public BankAccount getLloyds1();

	public BankAccount getLloyds2();

	public void delete(BankAccount eu);

	public BankAccount update(BankAccount eu);

	public BankAccount insert(BankAccount eu);
}
