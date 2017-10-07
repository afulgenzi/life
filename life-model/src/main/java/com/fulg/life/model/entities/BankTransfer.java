package com.fulg.life.model.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "banktransfer")
public class BankTransfer extends Item {

	@OneToOne
	BankAccount fromBankAccount;

	@OneToOne
	BankAccount toBankAccount;

	@Column(name = "useTargetCurrency")
	Boolean useTargetCurrency;

//	@Column(name = "date")
//	Date date;
//
//	@Column(name = "amount")
//	Double amount;
//
//	@Column(name = "conversionRate")
//	Double conversionRate;

	@OneToOne(cascade = {CascadeType.ALL})
	BankAccountMovement fromMovement;

	@OneToOne(cascade = {CascadeType.ALL})
	BankAccountMovement toMovement;

	public BankAccount getFromBankAccount() {
		return fromBankAccount;
	}

	public void setFromBankAccount(BankAccount fromBankAccount) {
		this.fromBankAccount = fromBankAccount;
	}

	public BankAccount getToBankAccount() {
		return toBankAccount;
	}

	public void setToBankAccount(BankAccount toBankAccount) {
		this.toBankAccount = toBankAccount;
	}

	public Boolean getUseTargetCurrency() {
		return useTargetCurrency;
	}

	public void setUseTargetCurrency(Boolean useTargetCurrency) {
		this.useTargetCurrency = useTargetCurrency;
	}

//	public Date getDate() {
//		return date;
//	}
//
//	public void setDate(Date date) {
//		this.date = date;
//	}
//
//	public Double getAmount() {
//		return amount;
//	}
//
//	public void setAmount(Double amount) {
//		this.amount = amount;
//	}
//
//	public Double getConversionRate() {
//		return conversionRate;
//	}
//
//	public void setConversionRate(Double conversionRate) {
//		this.conversionRate = conversionRate;
//	}

	public BankAccountMovement getFromMovement() {
		return fromMovement;
	}

	public void setFromMovement(BankAccountMovement fromMovement) {
		this.fromMovement = fromMovement;
	}

	public BankAccountMovement getToMovement() {
		return toMovement;
	}

	public void setToMovement(BankAccountMovement toMovement) {
		this.toMovement = toMovement;
	}

}
