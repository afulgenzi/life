package com.fulg.life.data;

import com.google.common.collect.Lists;

import java.util.List;

public class ImportBankMovementData {
	private BankAccountMovementData importedBankAccountMovement = null;
	private List<BankAccountMovementData> matchingBankAccountMovements = Lists.newArrayList();
	private List<BankAccountMovementData> otherBankAccountMovements = Lists.newArrayList();
	private boolean bankTransfer = false;
	private BankAccountData otherBankAccount;
	private int matchingPriority = 0;

	public BankAccountMovementData getImportedBankAccountMovement() {
		return importedBankAccountMovement;
	}

	public void setImportedBankAccountMovement(BankAccountMovementData importedBankAccountMovement) {
		this.importedBankAccountMovement = importedBankAccountMovement;
	}

	public List<BankAccountMovementData> getMatchingBankAccountMovements() {
		return matchingBankAccountMovements;
	}

	public void setMatchingBankAccountMovements(List<BankAccountMovementData> matchingBankAccountMovements) {
		this.matchingBankAccountMovements = matchingBankAccountMovements;
	}

	public List<BankAccountMovementData> getOtherBankAccountMovements() {
		return otherBankAccountMovements;
	}

	public void setOtherBankAccountMovements(List<BankAccountMovementData> otherBankAccountMovements) {
		this.otherBankAccountMovements = otherBankAccountMovements;
	}

	public boolean isBankTransfer() {
		return bankTransfer;
	}

	public void setBankTransfer(boolean bankTransfer) {
		this.bankTransfer = bankTransfer;
	}

	public BankAccountData getOtherBankAccount() {
		return otherBankAccount;
	}

	public void setOtherBankAccount(BankAccountData otherBankAccount) {
		this.otherBankAccount = otherBankAccount;
	}

	public int getMatchingPriority()
	{
		return matchingPriority;
	}

	public void setMatchingPriority(int matchingPriority)
	{
		this.matchingPriority = matchingPriority;
	}
}
