package com.fulg.life.data;

import com.google.common.collect.Lists;

import java.util.List;

public class ImportBankAccountMovementResult {
	private List<ImportBankMovementData> importMovements = Lists.newArrayList();
	private List<BankAccountMovementData> existingMovements = Lists.newArrayList();
	
	public List<ImportBankMovementData> getImportMovements() {
		return importMovements;
	}
	public void setImportMovements(List<ImportBankMovementData> importMovements) {
		this.importMovements = importMovements;
	}
	public List<BankAccountMovementData> getExistingMovements() {
		return existingMovements;
	}
	public void setExistingMovements(List<BankAccountMovementData> existingMovements) {
		this.existingMovements = existingMovements;
	}
}
