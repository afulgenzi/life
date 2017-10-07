package com.fulg.life.facade.converter.reverse;

import com.fulg.life.data.BankTransferData;
import com.fulg.life.model.entities.BankTransfer;
import com.fulg.life.service.BankTransferService;

import javax.annotation.Resource;

public class BankTransferReverseConverter extends AbstractReverseConverter<BankTransfer, BankTransferData> {
	@Resource
	BankTransferService bankTransferService;
	@Resource
	BankAccountReverseConverter bankAccountReverseConverter;
	@Resource
	BankAccountMovementReverseConverter bankAccountMovementReverseConverter;
	@Resource
	CurrencyReverseConverter currencyReverseConverter;
	@Resource
	UserReverseConverter userReverseConverter;

	@Override
	protected BankTransfer createTarget(Long pk) {
		BankTransfer transfer = null;
		if (pk != null) {
			transfer = (BankTransfer) bankTransferService.getOne(pk);
		} else {
			transfer = new BankTransfer();
		}
		return transfer;
	}

	@Override
	public BankTransfer populate(BankTransferData source, BankTransfer target) {
		if (source.getFromBankAccount() != null) {
			target.setFromBankAccount(bankAccountReverseConverter.convert(source.getFromBankAccount()));
		}
		if (source.getToBankAccount() != null) {
			target.setToBankAccount(bankAccountReverseConverter.convert(source.getToBankAccount()));
		}
//		target.setConversionRate(source.getConversionRate());
		if (source.getFromMovement() != null) {
			if (target.getFromMovement()!=null)
			{
				bankAccountMovementReverseConverter.convert(source.getFromMovement(), target.getFromMovement());
			}
			else
			{
				target.setFromMovement(bankAccountMovementReverseConverter.convert(source.getFromMovement()));
			}
		}
		if (source.getToMovement() != null) {
			if (target.getToMovement()!=null)
			{
				bankAccountMovementReverseConverter.convert(source.getToMovement(), target.getToMovement());
			}
			else
			{
				target.setToMovement(bankAccountMovementReverseConverter.convert(source.getToMovement()));
			}
		}
		target.setUseTargetCurrency(source.getUseTargetCurrency() == null ? Boolean.FALSE : source
				.getUseTargetCurrency());
		return target;
	}

}
