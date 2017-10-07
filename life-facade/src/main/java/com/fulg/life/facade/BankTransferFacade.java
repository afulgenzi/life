package com.fulg.life.facade;

import com.fulg.life.data.BankTransferData;
import com.fulg.life.service.exception.UnauthorizedOperationException;

public interface BankTransferFacade {

	BankTransferData insert(BankTransferData movement) throws UnauthorizedOperationException;

	BankTransferData update(BankTransferData movement) throws UnauthorizedOperationException;

	void delete(BankTransferData movement) throws UnauthorizedOperationException;
}
