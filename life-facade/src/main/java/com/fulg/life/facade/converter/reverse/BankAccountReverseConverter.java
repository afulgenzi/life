package com.fulg.life.facade.converter.reverse;

import com.fulg.life.data.BankAccountData;
import com.fulg.life.model.entities.BankAccount;
import com.fulg.life.service.BankAccountService;

import javax.annotation.Resource;

public class BankAccountReverseConverter extends AbstractReverseConverter<BankAccount, BankAccountData> {
	@Resource
	BankAccountService bankAccountService;
	@Resource
	UserReverseConverter userReverseConverter;
	@Resource
	CurrencyReverseConverter currencyReverseConverter;

	@Override
	protected BankAccount createTarget(Long pk) {
		BankAccount item = null;
		if (pk != null) {
			item = (BankAccount) bankAccountService.getOne(pk);
		} else {
			item = new BankAccount();
		}
		return item;
	}

	@Override
	public BankAccount populate(BankAccountData source, BankAccount target) {
		target.setBankName(source.getBankName());
		target.setAccountNumber(source.getAccountNumber());
		target.setSubscriptionDate(source.getSubscriptionDate());
		if (source.getUser() != null) {
			target.setUser(userReverseConverter.convert(source.getUser()));
		}
		if (source.getCurrency() != null) {
			target.setCurrency(currencyReverseConverter.convert(source.getCurrency()));
		}
		return target;
	}

}
