package com.fulg.life.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fulg.life.model.entities.BankAccountMovement;
import com.fulg.life.model.entities.BankTransfer;
import com.fulg.life.model.repository.BankAccountMovementRepository;
import com.fulg.life.model.repository.BankTransferRepository;
import com.fulg.life.service.BankTransferService;
import com.fulg.life.service.CurrencyService;

@Service
public class DefaultBankTransferService implements BankTransferService {
	private static final String BANK_TRANSFER_FROM = "Transfer from ";
	private static final String BANK_TRANSFER_TO = "Transfer to ";

	@Resource
	private BankTransferRepository bankTransferRepository;
	@Resource
	private BankAccountMovementRepository bankAccountMovementRepository;
	@Resource
	private CurrencyService currencyService;

	@Override
	public BankTransfer getBankTransferByMovement(BankAccountMovement movement) {
		return bankTransferRepository.findByBankAccountMovement(movement.getPk());
	}

	@Override
	@Transactional
	public BankTransfer insert(BankTransfer transfer) {
		// FromMovement
		BankAccountMovement fromMovement = new BankAccountMovement();
		fromMovement.setBankAccount(transfer.getFromBankAccount());
		fromMovement.setCurrency(transfer.getFromBankAccount().getCurrency());
		fromMovement.setDate(transfer.getFromMovement().getDate());
		fromMovement.setDescription("Transfer to " + transfer.getToBankAccount().getBankName() + "/"
				+ transfer.getToBankAccount().getAccountNumber());
		fromMovement.setEu(BankAccountMovement.TYPE_OUT);
//		if (transfer.getUseTargetCurrency()) {
//			Double amount = null;
//			if (transfer.getConversionRate() != null) {
//				amount = currencyService.convertAmount(transfer.getAmount(), transfer.getToBankAccount().getCurrency(),
//						transfer.getFromBankAccount().getCurrency(), transfer.getConversionRate().doubleValue());
//			} else {
//				amount = currencyService.convertAmount(transfer.getAmount(), transfer.getToBankAccount().getCurrency(),
//						transfer.getFromBankAccount().getCurrency());
//			}
//			fromMovement.setAmount(amount);
//		} else {
//			fromMovement.setAmount(transfer.getAmount());
//		}
		fromMovement.setAmount(transfer.getFromMovement().getAmount());
		bankAccountMovementRepository.save(fromMovement);

		// ToMovement
		BankAccountMovement toMovement = new BankAccountMovement();
		toMovement.setBankAccount(transfer.getToBankAccount());
		toMovement.setCurrency(transfer.getToBankAccount().getCurrency());
		toMovement.setDate(transfer.getToMovement().getDate());
		toMovement.setDescription("Transfer from " + transfer.getFromBankAccount().getBankName() + "/"
				+ transfer.getFromBankAccount().getAccountNumber());
		toMovement.setEu(BankAccountMovement.TYPE_IN);
//		if (transfer.getUseTargetCurrency()) {
//			toMovement.setAmount(transfer.getAmount());
//		} else {
//			Double amount = null;
//			if (transfer.getConversionRate() != null) {
//				amount = currencyService.convertAmount(transfer.getAmount(), transfer.getFromBankAccount()
//						.getCurrency(), transfer.getToBankAccount().getCurrency(), transfer.getConversionRate().doubleValue());
//			} else {
//				amount = currencyService.convertAmount(transfer.getAmount(), transfer.getFromBankAccount()
//						.getCurrency(), transfer.getToBankAccount().getCurrency());
//			}
//			toMovement.setAmount(amount);
//		}
		toMovement.setAmount(transfer.getToMovement().getAmount());
		bankAccountMovementRepository.save(toMovement);

		// Transfer
		transfer.setFromMovement(fromMovement);
		transfer.setToMovement(toMovement);
		bankTransferRepository.save(transfer);

		fromMovement.setBankTransfer(transfer);
		bankAccountMovementRepository.save(fromMovement);

		toMovement.setBankTransfer(transfer);
		bankAccountMovementRepository.save(toMovement);

		return transfer;
	}

	@Override
	@Transactional
	public BankTransfer update(BankTransfer transfer) {
		bankTransferRepository.save(transfer);

		bankAccountMovementRepository.save(transfer.getFromMovement());

		bankAccountMovementRepository.save(transfer.getToMovement());

		return transfer;
	}

	@Override
	@Transactional
	public void delete(BankTransfer transfer) {
		transfer.getFromMovement().setBankTransfer(null);
		bankAccountMovementRepository.save(transfer.getFromMovement());

		transfer.getToMovement().setBankTransfer(null);
		bankAccountMovementRepository.save(transfer.getToMovement());

		bankTransferRepository.delete(transfer);
		bankAccountMovementRepository.delete(transfer.getFromMovement());
		bankAccountMovementRepository.delete(transfer.getToMovement());
	}

	@Override
	public BankTransfer getOne(Long pk) {
		return bankTransferRepository.findOne(pk);
	}

	@Override
	public String getBankTransferMovementDescription(BankAccountMovement movement) {
		BankTransfer transfer = this.getBankTransferByMovement(movement);
		if (transfer.getFromMovement().getPk().equals(movement.getPk())) {
			return BANK_TRANSFER_TO + transfer.getToBankAccount().getDisplayName() + " | "
					+ transfer.getToBankAccount().getBankName();
		} else if (transfer.getToMovement().getPk().equals(movement.getPk())) {
			return BANK_TRANSFER_FROM + transfer.getFromBankAccount().getDisplayName() + " | "
					+ transfer.getToBankAccount().getBankName();
		}
		return movement.getDescription();
	}

}
