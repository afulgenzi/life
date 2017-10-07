package com.fulg.life.facade.converter.reverse;

import com.fulg.life.data.CurrencyData;
import com.fulg.life.model.entities.Currency;
import com.fulg.life.service.CurrencyService;

import javax.annotation.Resource;

public class CurrencyReverseConverter extends AbstractReverseConverter<Currency, CurrencyData> {
	@Resource
	CurrencyService currencyService;

	@Override
	protected Currency createTarget(Long pk) {
		Currency item = null;
		if (pk != null) {
			item = (Currency) currencyService.getOne(pk);
		} else {
			item = new Currency();
		}
		return item;
	}

	@Override
	public Currency populate(CurrencyData source, Currency target) {
		target.setAbbreviation(source.getAbbreviation());
		target.setAmountInEuro(source.getAmountInEuro());
		target.setAmountInLire(source.getAmountInLire());
		target.setCode(source.getCode());
		target.setDescription(source.getDescription());
		return target;
	}

}
