package com.fulg.life.facade;

import com.fulg.life.data.CurrencyData;

import java.util.List;

public interface CurrencyFacade {

	List<CurrencyData> getAll();

	Double getConversionRate(Long fromCurrency, Long toCurrency);
}
