package com.fulg.life.facade.converter;

import com.fulg.life.data.CurrencyData;
import com.fulg.life.model.entities.Currency;

public class CurrencyConverter extends AbstractConverter<CurrencyData, Currency> {
	private UserConverter userConverter;

	@Override
	protected CurrencyData createTarget() {
		return new CurrencyData();
	}

	@Override
	public CurrencyData populate(Currency source, CurrencyData target) {
		if (source != null) {
			target.setPk(source.getPk());
			target.setAbbreviation(source.getAbbreviation());
			target.setAmountInEuro(source.getAmountInEuro());
			target.setAmountInLire(source.getAmountInLire());
			target.setCode(source.getCode());
			target.setDescription(source.getDescription());
		}
		return target;
	}

	public UserConverter getUserConverter() {
		return userConverter;
	}

	public void setUserConverter(UserConverter userConverter) {
		this.userConverter = userConverter;
	}

}
