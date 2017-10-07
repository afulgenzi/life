package com.fulg.life.service.impl;

import javax.annotation.Resource;

import com.fulg.life.model.entities.BankAccount;
import com.fulg.life.model.entities.BankAccountOwner;
import com.fulg.life.model.entities.User;
import com.fulg.life.model.repository.BankAccountOwnerRepository;
import com.fulg.life.service.BankAccountOwnerService;
import org.springframework.stereotype.Service;

@Service
public class DefaultBankAccountOwnerService implements BankAccountOwnerService {
	@Resource
	private BankAccountOwnerRepository bankAccountOwnerRepository;

	@Override
	public Boolean canWrite(BankAccount bankAccount, User user) {
		BankAccountOwner owner = bankAccountOwnerRepository.findByBankAccountAndUser(bankAccount.getPk(), user.getUsername());
		if (owner!=null){
			return owner.getCanWrite();
		}
		return Boolean.FALSE;
	}

}
