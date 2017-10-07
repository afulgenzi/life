package com.fulg.life.facade;

import com.fulg.life.data.BankAccountMovementData;
import com.fulg.life.data.ImportBankMovementData;
import com.fulg.life.facade.data.YearlyMatchesCockpitData;
import com.fulg.life.model.entities.User;

import java.util.List;

public interface ImportBankAccountMovementFacade {
	List<ImportBankMovementData> importMovementsFromText(Long bankAccount, String movements, User user);
	List<ImportBankMovementData> loadImportMovements(Long bankAccount, int year, int month, User user);
	YearlyMatchesCockpitData getYearlyMatchesCockpitData(Long bankAccount, int year, User user);
	YearlyMatchesCockpitData.MonthlyMatchesCockpitData getMonthlyMatchesCockpitData(Long bankAccount, int year, int month, User user);
	Boolean isContentAvailable(Long bankAccount, int year, int month, User user);
	String getMovementKey(final BankAccountMovementData mov);
}
