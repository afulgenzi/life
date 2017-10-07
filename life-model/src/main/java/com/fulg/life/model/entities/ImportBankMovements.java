package com.fulg.life.model.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "importbankmovements")
public class ImportBankMovements extends Item {
	@ManyToOne
	BankAccount bankAccount;

	@Column(name = "YEAR")
	Integer year;

	@Column(name = "MONTH")
	Integer month;

	@Column(name = "MINDATE")
	Date minDate;

	@Column(name = "MAXDATE")
	Date maxDate;

	@Column(name = "MOVEMENTSTEXT")
	@Lob
	String movementsText;

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public String getMovementsText() {
		return movementsText;
	}

	public void setMovementsText(String movementsText) {
		this.movementsText = movementsText;
	}
}
