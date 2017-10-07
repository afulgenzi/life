package com.fulg.life.migration.service.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fulg.life.migration.service.AbstractMigrator;
import com.fulg.life.model.entities.BankAccount;
import com.fulg.life.model.entities.BankAccountMovement;
import com.fulg.life.model.entities.Currency;
import com.fulg.life.model.repository.BankAccountMovementRepository;
import com.fulg.life.model.repository.CurrencyRepository;
import com.fulg.life.msaccess.dao.AccessEntrateUsciteDao;
import com.fulg.life.msaccess.dao.AccessValuteDao;
import com.fulg.life.msaccess.entity.EntrateUscite;
import com.fulg.life.msaccess.entity.Valute;
import com.fulg.life.service.BankAccountService;
import com.fulg.life.service.CurrencyService;

@Component
public class EntrateUsciteToBankAccountMovementMigrator extends AbstractMigrator<EntrateUscite, BankAccountMovement> {
	private static final Logger LOG = LoggerFactory.getLogger(EntrateUsciteToBankAccountMovementMigrator.class);

	@Resource
	AccessEntrateUsciteDao accessEntrateUsciteDao;
	@Resource
	AccessValuteDao accessValuteDao;
	@Resource
	BankAccountMovementRepository bankAccountMovementRepository;
	// @Resource
	// CurrencyRepository currencyRepository;
	@Resource
	CurrencyService currencyService;
	@Resource
	BankAccountService bankAccountService;

	@Override
	public void clearAll() {
		bankAccountMovementRepository.deleteAll();
	}

	@Override
	public List<EntrateUscite> getAllSource() {
		return accessEntrateUsciteDao.getAll();
	}

	@Override
	public void migrateInternal(EntrateUscite source) {
		// BankAccount
		BankAccount bankAccount = getBankAccountForMovement(source);

		// Currency
		Currency movementCurrency = getCurrencyByIdValutaImporto(source.getIDValutaImporto());

		// BankAccountMovement
		BankAccountMovement bankAccountMovement = new BankAccountMovement();
		if (movementCurrency == null || bankAccount == null || bankAccount.getCurrency() == null) {
			LOG.warn("cannot compare currencies, set default amount!");
			bankAccountMovement.setAmount(source.getImporto());
			bankAccountMovement.setCurrency(movementCurrency);
		} else if (movementCurrency.getCode().equals(bankAccount.getCurrency().getCode())) {
			// Same currency between Movement and BankAccount
			bankAccountMovement.setAmount(source.getImporto());
			bankAccountMovement.setCurrency(movementCurrency);
		} else {
			if (movementCurrency.getCode().equals(currencyService.getLire().getCode())) {
				// Movement currency -> LIT
				bankAccountMovement.setAmount(source.getImporto());
				bankAccountMovement.setCurrency(movementCurrency);
			} else if (movementCurrency.getCode().equals(currencyService.getEuro().getCode())) {
				// Movement currency -> EUR
				bankAccountMovement.setAmount(source.getImporto() / bankAccount.getCurrency().getAmountInEuro());
				bankAccountMovement.setCurrency(bankAccount.getCurrency());
				LOG.info("Converte amount from " + source.getImporto() + " to " + bankAccountMovement.getAmount());
			} else {
				// Default
				bankAccountMovement.setAmount(source.getImporto());
				bankAccountMovement.setCurrency(movementCurrency);
			}
		}
		bankAccountMovement.setBankAccount(bankAccount);
		bankAccountMovement.setDate(source.getData());
		bankAccountMovement.setDescription(source.getDescrizione());
		bankAccountMovement.setFinancialDuty(null);
		bankAccountMovement.setEu(source.getEU());

		// this.amendMovement(bankAccountMovement);

		bankAccountMovementRepository.save(bankAccountMovement);
		bankAccountRepository.save(bankAccount);
	}

	protected BankAccount getBankAccountForMovement(EntrateUscite source) {
		Calendar dateOfFirstSalaryToMoveIntoBarclaysAccount = new GregorianCalendar(2014, 3, 20);

		if (dateOfFirstSalaryToMoveIntoBarclaysAccount.getTime().before(source.getData())){
			if (source.getDescrizione().toLowerCase().contains("casa londra")) {
				return bankAccountService.getLloyds1();
			} else if (source.getDescrizione().toLowerCase().contains("council tax")) {
				return bankAccountService.getLloyds1();
			} else if (source.getDescrizione().toLowerCase().contains("virgin media")) {
				return bankAccountService.getLloyds1();
			} else if (source.getDescrizione().toLowerCase().contains("utility warehouse")) {
				return bankAccountService.getLloyds1();
			} else if (source.getDescrizione().toLowerCase().contains("january")
					|| source.getDescrizione().toLowerCase().contains("february")
					|| source.getDescrizione().toLowerCase().contains("march")
					|| source.getDescrizione().toLowerCase().contains("april")
					|| source.getDescrizione().toLowerCase().contains("may")
					|| source.getDescrizione().toLowerCase().contains("june")
					|| source.getDescrizione().toLowerCase().contains("july")
					|| source.getDescrizione().toLowerCase().contains("august")
					|| source.getDescrizione().toLowerCase().contains("september")
					|| source.getDescrizione().toLowerCase().contains("october")
					|| source.getDescrizione().toLowerCase().contains("november") || source.getDescrizione().toLowerCase()
					.contains("december")) {
				return bankAccountService.getBarclays1();
			} else {
				return getDefaultBankAccount();
			}
		}else{
			return getDefaultBankAccount();
		}
	}

	// protected void amendMovement(BankAccountMovement movement){
	// if (movement.getAmount().equals(Double.valueOf(1850.0)) &&
	// movement.getDescription().toLowerCase().contains("")){
	// movement.setAmount(Double.valueOf(1550.0));
	// movement.setCurrency(currencyService.getBritishPound());
	// movement.setBankAccount(bankAccount);
	// }
	// }

	protected Currency getCurrencyByIdValutaImporto(Integer ID) {
		Valute valute = accessValuteDao.getByIdValute(ID);
		Currency currency = currencyService.getByCode(valute.getCodice());
		return currency;
	}
}
