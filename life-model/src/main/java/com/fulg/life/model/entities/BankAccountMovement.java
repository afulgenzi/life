package com.fulg.life.model.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "bankaccountmovements")
public class BankAccountMovement extends Item {
	public final static String TYPE_IN = "E";
	public final static String TYPE_OUT = "U";

	@ManyToOne
	BankAccount bankAccount;

	@ManyToOne(optional = true)
	FinancialDuty financialDuty;

	@ManyToOne(optional = true)
	Category category;

	@Column(name = "AMOUNT")
	Double amount;

	@ManyToOne
	Currency currency;

	@Column(name = "DATE")
	Date date;

	@Column(name = "DESCRIPTION")
	String description;

	@Column(name = "EU")
	String eu;

	@ManyToOne(optional = true, cascade = { CascadeType.ALL })
	BankTransfer bankTransfer;

	@ManyToMany(mappedBy="movements")
	List<Invoice> invoices;

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public FinancialDuty getFinancialDuty() {
		return financialDuty;
	}

	public void setFinancialDuty(FinancialDuty financialDuty) {
		this.financialDuty = financialDuty;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Category getCategory()
	{
		return category;
	}

	public void setCategory(Category category)
	{
		this.category = category;
	}

	public String getEu() {
		return eu;
	}

	public void setEu(String eu) {
		this.eu = eu;
	}

	public BankTransfer getBankTransfer() {
		return bankTransfer;
	}

	public void setBankTransfer(BankTransfer bankTransfer) {
		this.bankTransfer = bankTransfer;
	}

}
