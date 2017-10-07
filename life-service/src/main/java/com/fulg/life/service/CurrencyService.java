package com.fulg.life.service;

import java.util.List;

import com.fulg.life.model.entities.Currency;

public interface CurrencyService extends ItemService {
	public List<Currency> getAll();

	public Currency getByCode(String code);

	public Currency getEuro();

	public Currency getLire();

	public Currency getBritishPound();

	public Double convertAmount(Double amount, Currency fromCurrency, Currency toCurrency);

	public Double convertAmount(Double amount, Currency fromCurrency, Currency toCurrency, double conversionRate);

	public Double getDefaultConversionRate(Currency fromCurrency, Currency toCurrency);
}
