package com.fulg.life.data;

public class BankTransferData extends ItemData {
	private static final long serialVersionUID = 1L;

	BankAccountData fromBankAccount;
	BankAccountData toBankAccount;
	BankAccountMovementData fromMovement;
	BankAccountMovementData toMovement;
	Boolean useTargetCurrency;
	Double conversionRate;

	public BankAccountData getFromBankAccount()
	{
		return fromBankAccount;
	}

	public void setFromBankAccount(BankAccountData fromBankAccount)
	{
		this.fromBankAccount = fromBankAccount;
	}

	public BankAccountData getToBankAccount()
	{
		return toBankAccount;
	}

	public void setToBankAccount(BankAccountData toBankAccount)
	{
		this.toBankAccount = toBankAccount;
	}

	public BankAccountMovementData getFromMovement()
	{
		return fromMovement;
	}

	public void setFromMovement(BankAccountMovementData fromMovement)
	{
		this.fromMovement = fromMovement;
	}

	public BankAccountMovementData getToMovement()
	{
		return toMovement;
	}

	public void setToMovement(BankAccountMovementData toMovement)
	{
		this.toMovement = toMovement;
	}

	public Boolean getUseTargetCurrency()
	{
		return useTargetCurrency;
	}

	public void setUseTargetCurrency(Boolean useTargetCurrency)
	{
		this.useTargetCurrency = useTargetCurrency;
	}

	public Double getConversionRate()
	{
		return conversionRate;
	}

	public void setConversionRate(Double conversionRate)
	{
		this.conversionRate = conversionRate;
	}
}
