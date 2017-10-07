package com.fulg.life.model.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "bankaccounts")
public class BankAccount extends Item {

    @Column(name = "BANKNAME")
    String bankName;

    @ManyToOne(optional = true)
    Bank bank;

    @Column(name = "ACCOUNTNUMBER")
    String accountNumber;

    @Column(name = "DISPLAYNAME")
    String displayName;

    @Column(name = "IBAN")
    String iban;

    @Column(name = "BIC")
    String bic;

    @ManyToOne(optional = true)
    User user;

    @OneToMany
    List<BankAccountMovement> movements;

    @Column(name = "SUBSCRIPTIONDATE")
    Date subscriptionDate;

    @Column(name = "ISDEFAULT")
    Boolean defaultAccount;

    @Column(name = "ACTIVE")
    Boolean active;

    @ManyToOne
    Currency currency;

    public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
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

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<BankAccountMovement> getMovements() {
        return movements;
    }

    public void setMovements(List<BankAccountMovement> movements) {
        this.movements = movements;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

	public Boolean getDefaultAccount() {
		return defaultAccount;
	}

	public void setDefaultAccount(Boolean defaultAccount) {
		this.defaultAccount = defaultAccount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
