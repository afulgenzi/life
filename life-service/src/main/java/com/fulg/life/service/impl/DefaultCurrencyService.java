package com.fulg.life.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fulg.life.model.entities.Currency;
import com.fulg.life.model.repository.CurrencyRepository;
import com.fulg.life.service.CurrencyService;
import com.googlecode.ehcache.annotations.Cacheable;

@Service
public class DefaultCurrencyService extends DefaultItemService implements CurrencyService {
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.getLogger(DefaultCurrencyService.class);

	private static final String CURRENCY_CODE_GBP = "currency.code.gbp";
	private static final String CURRENCY_CODE_EUR = "currency.code.eur";
	private static final String CURRENCY_CODE_LIT = "currency.code.lit";

	@Resource(name = "lifeServiceProperties")
	private Properties properties;
	@Resource
	private CurrencyRepository currencyRepository;
	
	private Map<String, Currency> myCacheMap = new HashMap<String, Currency>();

	@Override
	@Cacheable(cacheName="currencyGetAll")
	public List<Currency> getAll() {
		List<Currency> items = currencyRepository.findAll();
		for (Currency item : items) {
			addItemToCache(item);
		}
		return items;
	}

	@Override
	@Cacheable(cacheName="currencyGetOne")
	public Currency getOne(Long pk) {
//		Item item = getItemFromCache(pk);
//		if (item == null) {
			LOG.info("SEARCHING CURRENCY FOR PK: " + pk);
			Currency currency = currencyRepository.findOne(pk);
//			if (currency != null) {
//				addItemToCache(currency);
//			}
			return currency;
//		} else {
//			return (Currency) (item);
//		}
	}

	@Override
	@Cacheable(cacheName="currencyGetEuro")
	public Currency getEuro() {
		String code = properties.getProperty(CURRENCY_CODE_EUR);

		return getByCode(code);
	}

	@Override
	@Cacheable(cacheName="currencyGetLire")
	public Currency getLire() {
		String code = properties.getProperty(CURRENCY_CODE_LIT);

		return getByCode(code);
	}

	@Override
	@Cacheable(cacheName="currencyGetPound")
	public Currency getBritishPound() {
		String code = properties.getProperty(CURRENCY_CODE_GBP);

		return getByCode(code);
	}

	@Override
	@Cacheable(cacheName="currencyGetByCode")
	public Currency getByCode(String code) {
		
		Currency currency = (Currency)getItemFromCacheByCode(code);
		if (currency == null){
			LOG.info("currencyGetByCode - getting value from db");
			currency = currencyRepository.findByCode(code);
			
			if (currency != null) {
				addItemToCacheByCode(currency.getCode(), currency);
			}
		}else{
			LOG.debug("currencyGetByCode - getting value from cache");
		}

		return currency;
	}
	
	@Override
	public Double convertAmount(Double amount, Currency fromCurrency, Currency toCurrency) {
		return amount * getDefaultConversionRate(fromCurrency, toCurrency);
	}
	
	@Override
	public Double convertAmount(Double amount, Currency fromCurrency, Currency toCurrency, double conversionRate) {
		return amount * conversionRate;
	}

	@Override
	public Double getDefaultConversionRate(Currency fromCurrency, Currency toCurrency) {
		if (toCurrency.getCode().equalsIgnoreCase(getEuro().getCode())) {
			// X -> EUR
			return fromCurrency.getAmountInEuro();
		} else if (fromCurrency.getCode().equalsIgnoreCase(getEuro().getCode())) {
			// EUR -> X
			return 1 / toCurrency.getAmountInEuro();
		} else if (toCurrency.getCode().equalsIgnoreCase(fromCurrency.getCode())) {
			// X -> X
			return 1d;
		} else {
			throw new RuntimeException("Cannot convert amount from " + fromCurrency.getCode() + " to "
					+ toCurrency.getCode());
		}
	}
}
