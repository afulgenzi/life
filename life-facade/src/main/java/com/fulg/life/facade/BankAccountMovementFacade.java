package com.fulg.life.facade;

import com.fulg.life.data.BankAccountData;
import com.fulg.life.data.BankAccountMovementData;
import com.fulg.life.data.CategoryData;
import com.fulg.life.data.FrequencyData;
import com.fulg.life.model.entities.User;
import com.fulg.life.service.exception.UnauthorizedOperationException;

import java.util.Date;
import java.util.List;

public interface BankAccountMovementFacade {

	List<BankAccountMovementData> getAll(Long bankAccount, User user);

	List<BankAccountMovementData> getByMonth(Long bankAccount, int year, int month, User user);

	List<BankAccountMovementData> getByText(Long bankAccount, String text, CategoryData category, Date startDate, Date endDate, String inOut, Boolean uncategorised, User user);

	List<BankAccountMovementData> getByCategory(Long categoryPk, final User user);

	List<BankAccountMovementData> getByTransfer(String fromTo, BankAccountData account, BankAccountData otherAccount, Date startDate, Date endDate, User user);

	BankAccountMovementData update(BankAccountMovementData movement, User currentUser)
			throws UnauthorizedOperationException;

	List<BankAccountMovementData> updateAll(List<BankAccountMovementData> movement, User currentUser)
			throws UnauthorizedOperationException;

	List<BankAccountMovementData> insertAll(BankAccountMovementData movement, FrequencyData frequency
			, Date fromDate
			, Date untilDate
			, boolean skipFirstDate
			, User currentUser)
			throws UnauthorizedOperationException;

	BankAccountMovementData insert(BankAccountMovementData movement, User currentUser)
			throws UnauthorizedOperationException;

	List<BankAccountMovementData> insertAll(List<BankAccountMovementData> movement, User currentUser)
			throws UnauthorizedOperationException;

	void delete(BankAccountMovementData movement, User currentUser) throws UnauthorizedOperationException;
}
