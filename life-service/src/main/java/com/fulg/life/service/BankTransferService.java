package com.fulg.life.service;

import com.fulg.life.model.entities.BankAccountMovement;
import com.fulg.life.model.entities.BankTransfer;

public interface BankTransferService extends ItemService {
	public BankTransfer getBankTransferByMovement(BankAccountMovement movement);

	public BankTransfer insert(BankTransfer transfer);

	public BankTransfer update(BankTransfer transfer);

	public void delete(BankTransfer transfer);

	public String getBankTransferMovementDescription(BankAccountMovement movement);
}
