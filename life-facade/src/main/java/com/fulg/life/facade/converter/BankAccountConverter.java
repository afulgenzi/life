package com.fulg.life.facade.converter;

import com.fulg.life.data.BankAccountData;
import com.fulg.life.model.entities.BankAccount;

import javax.annotation.Resource;

public class BankAccountConverter extends AbstractConverter<BankAccountData, BankAccount> {
	@Resource
	private UserConverter userConverter;
	@Resource
	private CurrencyConverter currencyConverter;
	@Resource
	private BankConverter bankConverter;

	@Override
	protected BankAccountData createTarget() {
		return new BankAccountData();
	}

	@Override
	public BankAccountData populate(BankAccount source, BankAccountData target) {
		if (source != null) {
			target.setPk(source.getPk());
			target.setBankName(source.getBankName());
			target.setAccountNumber(source.getAccountNumber());
//			target.setDisplayName(source.getBankName().substring(
//					0,
//					source.getBankName().indexOf(" ") == -1 ? source.getBankName().length() : source.getBankName()
//							.indexOf(" "))
//					+ " - "
//					+ source.getAccountNumber().substring(source.getAccountNumber().length() - 4,
//							source.getAccountNumber().length()));
			target.setDisplayName(source.getDisplayName());
			target.setSubscriptionDate(source.getSubscriptionDate());
			target.setUser(userConverter.convert(source.getUser()));
			target.setCurrency(currencyConverter.convert(source.getCurrency()));
			target.setBank(bankConverter.convert(source.getBank()));
		}
		return target;
	}
}
