package com.fulg.life.facade.impl;

import com.fulg.life.data.BankAccountData;
import com.fulg.life.facade.BankAccountFacade;
import com.fulg.life.facade.converter.BankAccountConverter;
import com.fulg.life.facade.converter.reverse.BankAccountReverseConverter;
import com.fulg.life.model.entities.BankAccount;
import com.fulg.life.model.entities.User;
import com.fulg.life.service.BankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

public class BankAccountFacadeImpl implements BankAccountFacade {
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.getLogger(BankAccountFacadeImpl.class);

	@Resource
	BankAccountService bankAccountService;
	@Resource
	BankAccountConverter bankAccountConverter;
	@Resource
	BankAccountReverseConverter bankAccountReverseConverter;

	/**
	 * getAll
	 * 
	 * @return
	 */
	@Override
	public List<BankAccountData> getAll(User user) {
		// get movements
		List<BankAccount> movementList = bankAccountService.getAll(user);

		// prepare output
		return prepareData(movementList);
	}

	@Override
	public BankAccountData getById(final Long id, final User user) {
		// get movements
		BankAccount bankAccount = bankAccountService.getByPk(id);

		// prepare output
		return bankAccountConverter.convert(bankAccount);
	}

	protected List<BankAccountData> prepareData(List<BankAccount> inputItems) {
		// convert
		List<BankAccountData> items = bankAccountConverter.convertAll(inputItems);

		// sort
		Collections.sort(items);

		return items;
	}

	// UPDATE
	public BankAccountData update(BankAccountData itemData) {
		BankAccount item = bankAccountReverseConverter.convert(itemData);
		bankAccountService.update(item);
		return bankAccountConverter.convert(item);
	}

	// INSERT
	public BankAccountData insert(BankAccountData movementData) {
		BankAccount movement = bankAccountReverseConverter.convert(movementData);
		movement = bankAccountService.insert(movement);
		return bankAccountConverter.convert(movement);
	}

	// DELETE
	public void delete(BankAccountData movementData) {
		BankAccount movement = bankAccountReverseConverter.convert(movementData);
		bankAccountService.delete(movement);
	}

}
