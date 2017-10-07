package com.fulg.life.data;

import java.util.Date;

public class BankAccountData extends ItemData implements Comparable<BankAccountData> {
	String bankName;
	String accountNumber;
	String displayName;
	Date subscriptionDate;
	UserData user;
	CurrencyData currency;
	Boolean checked;
	Boolean disabled;
	BankData bank;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Date getSubscriptionDate() {
		return subscriptionDate;
	}

	public void setSubscriptionDate(Date subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}

	public UserData getUser() {
		return user;
	}

	public void setUser(UserData user) {
		this.user = user;
	}

	public int compareTo(BankAccountData o) {
		BankAccountData item = (BankAccountData) o;
		return item.getAccountNumber().compareTo(o.getAccountNumber());
	}

	public CurrencyData getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyData currency) {
		this.currency = currency;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getDisabled()
	{
		return disabled;
	}

	public void setDisabled(Boolean disabled)
	{
		this.disabled = disabled;
	}

	public BankData getBank() {
		return bank;
	}

	public void setBank(BankData bank) {
		this.bank = bank;
	}

}
