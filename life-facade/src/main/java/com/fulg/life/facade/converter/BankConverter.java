package com.fulg.life.facade.converter;

import com.fulg.life.data.BankData;
import com.fulg.life.model.entities.Bank;

public class BankConverter extends AbstractConverter<BankData, Bank> {

	@Override
	protected BankData createTarget() {
		return new BankData();
	}

	@Override
	public BankData populate(Bank source, BankData target) {
		if (source != null) {
			target.setPk(source.getPk());
			target.setCode(source.getCode());
			target.setName(source.getName());
		}
		return target;
	}
}
