package com.fulg.life.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "financialduties")
public class FinancialDuty extends Duty {
    public enum IO {
        IN, OUT
    }

    @Column(name = "AMOUNT")
    Double amount;

    @Column(name = "PAID")
    boolean paid;

    @Column(name = "CURRENCYID")
    Integer currencyId;

    @Column(name = "IO")
    IO io;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

}
