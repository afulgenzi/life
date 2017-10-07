package com.fulg.life.service;

import com.fulg.life.model.entities.Bank;
import com.fulg.life.model.entities.BankAccountMovement;
import com.fulg.life.model.entities.Currency;

import java.util.List;

public interface BankService extends ItemService {
	List<Bank> getAll();

	Bank getByCode(String code);

	Bank getBarclays();
	Bank getLloyds();
	Bank getIntesa();

	Bank update(Bank eu);
	Bank insert(Bank eu);
	void delete(Bank eu);
}
