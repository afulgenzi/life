package com.fulg.life.facade.impl;

import com.fulg.life.data.CurrencyData;
import com.fulg.life.facade.CurrencyFacade;
import com.fulg.life.facade.converter.CurrencyConverter;
import com.fulg.life.facade.converter.reverse.CurrencyReverseConverter;
import com.fulg.life.model.entities.Currency;
import com.fulg.life.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

public class CurrencyFacadeImpl implements CurrencyFacade {
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.getLogger(CurrencyFacadeImpl.class);

	@Resource
	CurrencyService currencyService;
	@Resource
	CurrencyConverter currencyConverter;
	@Resource
	CurrencyReverseConverter currencyReverseConverter;

	/**
	 * getAll
	 * 
	 * @return
	 */
	@Override
	public List<CurrencyData> getAll() {
		// get movements
		List<Currency> itemList = currencyService.getAll();

		// prepare output
		return prepareData(itemList);
	}

	protected List<CurrencyData> prepareData(List<Currency> inputItems) {
		// convert
		List<CurrencyData> items = currencyConverter.convertAll(inputItems);

		// sort
		Collections.sort(items);

		return items;
	}

	@Override
	public Double getConversionRate(Long fromCurrencyPK, Long toCurrencyPK) {
		Currency fromCurrency = (Currency) currencyService.getOne(fromCurrencyPK);
		Currency toCurrency = (Currency) currencyService.getOne(toCurrencyPK);
		return currencyService.getDefaultConversionRate(fromCurrency, toCurrency);
	}

}
