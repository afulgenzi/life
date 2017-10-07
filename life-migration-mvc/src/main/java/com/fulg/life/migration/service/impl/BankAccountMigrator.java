package com.fulg.life.migration.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.fulg.life.migration.service.AbstractMigrator;
import com.fulg.life.model.entities.BankAccount;
import com.fulg.life.model.entities.BankAccountMovement;
import com.fulg.life.model.entities.User;
import com.fulg.life.model.repository.BankAccountRepository;
import com.fulg.life.service.CurrencyService;

@Component
public class BankAccountMigrator extends AbstractMigrator<BankAccount, BankAccount> {
	@Resource
	BankAccountRepository bankAccountRepository;
	@Resource
	CurrencyService currencyService;

	private static final String INTESA_ACCOUNT_NUMBER = "100000005056";
	private static final String INTESA_BANK_NAME = "Intesa SanPaolo";
	private static final String INTESA2_ACCOUNT_NUMBER = "100000005455";
	private static final String LLOYDS_ACCOUNT_NUMBER = "6909206";
	private static final String LLOYDS_BANK_NAME = "Lloyds TSB";
	private static final String BARCLAYS_ACCOUNT_NUMBER = "73575217";
	private static final String BARCLAYS2_ACCOUNT_NUMBER = "63842312";
	private static final String BARCLAYS_BANK_NAME = "Barclays";

	@Override
	public void clearAll() {
		bankAccountRepository.deleteAll();
	}

	@Override
	public List<BankAccount> getAllSource() {
		List<BankAccount> bankAccountList = new ArrayList<BankAccount>();
		
		BankAccount defaultBankAccount = getDefaultBankAccount();
		User defaultUser = getDefaultUser();
		
		if (defaultBankAccount == null) {
			defaultBankAccount = new BankAccount();
			defaultBankAccount.setBankName(INTESA_BANK_NAME);
			defaultBankAccount.setAccountNumber(INTESA_ACCOUNT_NUMBER);
			defaultBankAccount.setDefaultAccount(Boolean.TRUE);
			defaultBankAccount.setMovements(new ArrayList<BankAccountMovement>());
			defaultBankAccount.setSubscriptionDate(new Date());
			defaultBankAccount.setCurrency(currencyService.getEuro());
			defaultBankAccount.setUser(defaultUser);
			
		}
		bankAccountList.add(defaultBankAccount);

		BankAccount account = null;
		
		// Intesa2
		account = new BankAccount();
		account.setBankName(INTESA_BANK_NAME);
		account.setAccountNumber(INTESA2_ACCOUNT_NUMBER);
		account.setDefaultAccount(Boolean.FALSE);
		account.setMovements(new ArrayList<BankAccountMovement>());
		account.setSubscriptionDate(new Date());
		account.setUser(defaultUser);
		account.setCurrency(currencyService.getEuro());
		bankAccountList.add(account);
		
		// Lloyds TSB
		account = new BankAccount();
		account.setBankName(LLOYDS_BANK_NAME);
		account.setAccountNumber(LLOYDS_ACCOUNT_NUMBER);
		account.setDefaultAccount(Boolean.FALSE);
		account.setMovements(new ArrayList<BankAccountMovement>());
		account.setSubscriptionDate(new Date());
		account.setUser(defaultUser);
		account.setCurrency(currencyService.getBritishPound());
		bankAccountList.add(account);
		
		// Barclays (main)
		account = new BankAccount();
		account.setBankName(BARCLAYS_BANK_NAME);
		account.setAccountNumber(BARCLAYS_ACCOUNT_NUMBER);
		account.setDefaultAccount(Boolean.FALSE);
		account.setMovements(new ArrayList<BankAccountMovement>());
		account.setSubscriptionDate(new Date());
		account.setUser(defaultUser);
		account.setCurrency(currencyService.getBritishPound());
		bankAccountList.add(account);
		
		// Barclays (save)
		account = new BankAccount();
		account.setBankName(BARCLAYS_BANK_NAME);
		account.setAccountNumber(BARCLAYS2_ACCOUNT_NUMBER);
		account.setDefaultAccount(Boolean.FALSE);
		account.setMovements(new ArrayList<BankAccountMovement>());
		account.setSubscriptionDate(new Date());
		account.setUser(defaultUser);
		account.setCurrency(currencyService.getBritishPound());
		bankAccountList.add(account);
		
		return bankAccountList;
	}

	@Override
	public void migrateInternal(BankAccount source) {
		bankAccountRepository.save(source);
	}

}
