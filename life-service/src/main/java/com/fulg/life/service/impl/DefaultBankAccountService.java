package com.fulg.life.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fulg.life.model.entities.BankAccount;
import com.fulg.life.model.entities.User;
import com.fulg.life.model.repository.BankAccountRepository;
import com.fulg.life.service.BankAccountService;
import com.fulg.life.service.SessionService;
import com.fulg.life.service.UserService;

@Service
public class DefaultBankAccountService implements BankAccountService {
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.getLogger(DefaultBankAccountService.class);
	
	private static final String ACCOUNT_BANK_INTESA = "account.bank.intesa";
	private static final String ACCOUNT_BANK_LLOYDS = "account.bank.lloyds";
	private static final String ACCOUNT_BANK_BARCLAYS = "account.bank.barclays";

	private static final String ACCOUNT_CODE_INTESA1 = "account.code.intesa1";
	private static final String ACCOUNT_CODE_INTESA2 = "account.code.intesa2";
	private static final String ACCOUNT_CODE_LLOYDS1 = "account.code.lloyds1";
	private static final String ACCOUNT_CODE_LLOYDS2 = "account.code.lloyds2";
	private static final String ACCOUNT_CODE_BARCLAYS1 = "account.code.barclays1";
	private static final String ACCOUNT_CODE_BARCLAYS2 = "account.code.barclays2";


	@Resource
	private BankAccountRepository bankAccountRepository;
	@Resource
	private UserService userService;
	@Resource
	private SessionService sessionService;
	@Resource(name = "lifeServiceProperties")
	private Properties properties;


	@Override
	public List<BankAccount> getAll(User user) {
		List<BankAccount> items = bankAccountRepository.findAll();

		return filterListByCurrentUser(items, user);
	}

	@Override
	public BankAccount getOne(Long pk) {
		return bankAccountRepository.findOne(pk);
	}

	@Override
	public BankAccount update(BankAccount eu) {
		bankAccountRepository.save(eu);
		return eu;
	}

	@Override
	public BankAccount insert(BankAccount eu) {
		bankAccountRepository.save(eu);
		return eu;
	}

	@Override
	public void delete(BankAccount eu) {
		bankAccountRepository.delete(eu);
	}

	protected List<BankAccount> filterListByCurrentUser(List<BankAccount> items, User user) {
		return filterListByUser(items, user);
	}

	protected List<BankAccount> filterListByUser(List<BankAccount> items, User user) {
		List<BankAccount> retList = new ArrayList<BankAccount>();
		if (user != null) {
			for (BankAccount item : items) {
				if (Boolean.TRUE.equals(item.getActive())){
					if (belongsToUser(item, user)) {
						retList.add(item);
					}
				}
			}
		}
		return retList;
	}

	public boolean belongsToUser(BankAccount item, User user) {
		if (item != null && user != null) {
			if (user.getPk().equals(item.getUser().getPk())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public BankAccount getByPk(final Long pk) {
		return getOne(pk);
	}

	@Override
	public BankAccount getIntesa1() {
		String bankName = properties.getProperty(ACCOUNT_BANK_INTESA);
		String accountNumber = properties.getProperty(ACCOUNT_CODE_INTESA1);

		BankAccount account = bankAccountRepository.findByBankAndAccountCode(accountNumber, bankName);

		return account;
	}

	@Override
	public BankAccount getIntesa2() {
		String bankName = properties.getProperty(ACCOUNT_BANK_INTESA);
		String accountNumber = properties.getProperty(ACCOUNT_CODE_INTESA2);
		
		BankAccount account = bankAccountRepository.findByBankAndAccountCode(accountNumber, bankName);
		
		return account;
	}

	@Override
	public BankAccount getBarclays1() {
		String bankName = properties.getProperty(ACCOUNT_BANK_BARCLAYS);
		String accountNumber = properties.getProperty(ACCOUNT_CODE_BARCLAYS1);
		
		BankAccount account = bankAccountRepository.findByBankAndAccountCode(accountNumber, bankName);
		
		return account;
	}

	@Override
	public BankAccount getBarclays2() {
		String bankName = properties.getProperty(ACCOUNT_BANK_BARCLAYS);
		String accountNumber = properties.getProperty(ACCOUNT_CODE_BARCLAYS2);
		
		BankAccount account = bankAccountRepository.findByBankAndAccountCode(accountNumber, bankName);
		
		return account;
	}

	@Override
	public BankAccount getLloyds1() {
		String bankName = properties.getProperty(ACCOUNT_BANK_LLOYDS);
		String accountNumber = properties.getProperty(ACCOUNT_CODE_LLOYDS1);
		
		BankAccount account = bankAccountRepository.findByBankAndAccountCode(accountNumber, bankName);
		
		return account;
	}

	@Override
	public BankAccount getLloyds2() {
		String bankName = properties.getProperty(ACCOUNT_BANK_LLOYDS);
		String accountNumber = properties.getProperty(ACCOUNT_CODE_LLOYDS2);

		BankAccount account = bankAccountRepository.findByBankAndAccountCode(accountNumber, bankName);

		return account;
	}

}
