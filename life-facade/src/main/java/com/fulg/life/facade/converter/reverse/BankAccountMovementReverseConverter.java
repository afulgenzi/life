package com.fulg.life.facade.converter.reverse;

import com.fulg.life.data.BankAccountMovementData;
import com.fulg.life.model.entities.BankAccountMovement;
import com.fulg.life.service.BankAccountMovementService;

import javax.annotation.Resource;

public class BankAccountMovementReverseConverter extends
		AbstractReverseConverter<BankAccountMovement, BankAccountMovementData> {
	@Resource
	BankAccountMovementService bankAccountMovementService;
	@Resource
	BankAccountReverseConverter bankAccountReverseConverter;
	@Resource
	CurrencyReverseConverter currencyReverseConverter;
	@Resource
	CategoryReverseConverter categoryReverseConverter;
	@Resource
	UserReverseConverter userReverseConverter;

	@Override
	protected BankAccountMovement createTarget(Long pk) {
		BankAccountMovement movement = null;
		if (pk != null) {
			movement = (BankAccountMovement) bankAccountMovementService.getOne(pk);
		} else {
			movement = new BankAccountMovement();
		}
		return movement;
	}

	@Override
	public BankAccountMovement populate(BankAccountMovementData source, BankAccountMovement target) {
		if (source.getBankAccount() != null) {
			target.setBankAccount(bankAccountReverseConverter.convert(source.getBankAccount()));
		}
		if (source.getCurrency() != null) {
			target.setCurrency(currencyReverseConverter.convert(source.getCurrency()));
		}
		if (source.getCategory() != null && source.getCategory().getPk() != null) {
			target.setCategory(categoryReverseConverter.convert(source.getCategory()));
		}
		target.setAmount(source.getAmount());
		target.setEu(source.getEu());
		target.setDate(source.getDate());
		target.setDescription(source.getDescription());
		return target;
	}

}
