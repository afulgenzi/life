package com.fulg.life.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bankaccount_owner")
public class BankAccountOwner extends Item {

	@ManyToOne
	BankAccount bankAccount;

	@ManyToOne
	User user;

	@Column(name = "CANWRITE")
	Boolean canWrite;

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getCanWrite() {
		return canWrite;
	}

	public void setCanWrite(Boolean canWrite) {
		this.canWrite = canWrite;
	}
}
