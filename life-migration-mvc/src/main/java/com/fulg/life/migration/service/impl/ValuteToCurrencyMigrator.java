package com.fulg.life.migration.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.fulg.life.migration.service.AbstractMigrator;
import com.fulg.life.model.entities.Currency;
import com.fulg.life.model.repository.BankAccountMovementRepository;
import com.fulg.life.model.repository.CurrencyRepository;
import com.fulg.life.msaccess.dao.AccessValuteDao;
import com.fulg.life.msaccess.entity.Valute;

@Component
public class ValuteToCurrencyMigrator extends AbstractMigrator<Valute, Currency> {
	private static final String POUND_ABBREVIATION = "£.";
	private static final double POUND_AMOUNT_IN_EURO = 1.18;
	private static final double POUND_AMOUNT_IN_LIRE = 2200.0;
	private static final String POUND_CODE = "GBP";
	private static final String POUND_DESCRIPTION = "British Pound";
	
	@Resource
	AccessValuteDao accessValuteDao;
	@Resource
	CurrencyRepository currencyRepository;
	@Resource
	BankAccountMovementRepository bankAccountMovementRepository;

	@Override
	public void clearAll() {
		bankAccountMovementRepository.deleteAll();
		bankAccountRepository.deleteAll();
		currencyRepository.deleteAll();
	}

	@Override
	public List<Valute> getAllSource() {
		return accessValuteDao.getAll();
	}

	@Override
	public void migrateInternal(Valute source) {
		Currency currency = new Currency();

		currency.setAbbreviation(source.getAbbreviazione());
		currency.setAmountInEuro(source.getValoreInEuro());
		currency.setAmountInLire(source.getValoreInLire().doubleValue());
		currency.setCode(source.getCodice());
		currency.setDescription(source.getDescrizione());

		currencyRepository.save(currency);
	}

	@Override
	public int createAdditionalTargets() {
		Currency pound = new Currency();
		pound.setAbbreviation(POUND_ABBREVIATION);
		pound.setAmountInEuro(POUND_AMOUNT_IN_EURO);
		pound.setAmountInLire(POUND_AMOUNT_IN_LIRE);
		pound.setCode(POUND_CODE);
		pound.setDescription(POUND_DESCRIPTION);
		currencyRepository.save(pound);
		
		return 1;
	}
}
