package com.fulg.life.webmvc.data;

import javax.validation.constraints.NotNull;

import com.fulg.life.data.BankAccountData;

/**
 * Created by alessandro.fulgenzi on 10/02/16.
 */
public class ImportForm
{
    @NotNull(message = "{general.required}")
	private BankAccountData bankAccount;
    private String payload;
    private Integer year;
    private Integer month;

    public BankAccountData getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountData bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
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
}
