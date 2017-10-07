package com.fulg.life.facade.impl;

import com.fulg.life.data.BankAccountMovementData;
import com.fulg.life.data.BankTransferData;
import com.fulg.life.facade.BankTransferFacade;
import com.fulg.life.facade.converter.BankAccountMovementConverter;
import com.fulg.life.facade.converter.BankTransferConverter;
import com.fulg.life.facade.converter.reverse.BankTransferReverseConverter;
import com.fulg.life.model.entities.BankTransfer;
import com.fulg.life.service.BankAccountMovementService;
import com.fulg.life.service.BankTransferService;
import com.fulg.life.service.exception.UnauthorizedOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class BankTransferFacadeImpl implements BankTransferFacade {
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.getLogger(BankTransferFacadeImpl.class);

	@Resource
	BankTransferService bankTransferService;
	@Resource
	BankTransferConverter bankTransferConverter;
	@Resource
	BankTransferReverseConverter bankTransferReverseConverter;
	@Resource
	private BankAccountMovementConverter bankAccountMovementConverter;

	// INSERT
	@Override
	public BankTransferData insert(BankTransferData transferData)
			throws UnauthorizedOperationException {
		BankTransfer transfer = bankTransferReverseConverter.convert(transferData);
		transfer = bankTransferService.insert(transfer);
		return convertTransfer(transfer);
	}

	// UPDATE
	@Override
	public BankTransferData update(BankTransferData transferData)
			throws UnauthorizedOperationException {
		BankTransfer transfer = bankTransferReverseConverter.convert(transferData);

		bankTransferService.update(transfer);

		return convertTransfer(transfer);
	}

	// DELETE
	@Override
	public void delete(BankTransferData transferData) throws UnauthorizedOperationException {
		BankTransfer movement = bankTransferReverseConverter.convert(transferData);
		bankTransferService.delete(movement);
	}

	protected BankTransferData convertTransfer(BankTransfer transfer){
		 BankAccountMovementData fromMovement = bankAccountMovementConverter.convert(transfer.getFromMovement());
		 BankAccountMovementData toMovement = bankAccountMovementConverter.convert(transfer.getToMovement());
		 BankTransferData transferData = bankTransferConverter.convert(transfer);
		 transferData.setFromMovement(fromMovement);
		 transferData.setToMovement(toMovement);
		 return transferData;
	}
}
