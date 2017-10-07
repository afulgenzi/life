package com.fulg.life.webmvc.data;

import com.fulg.life.data.BankAccountData;

import java.util.Date;

/**
 * Created by alessandro.fulgenzi on 25/06/16.
 */
public class SearchTransferForm {
    private BankAccountData bankAccount;
    private String fromTo;
    private BankAccountData otherBankAccount;
    private Date startDate;
    private Date endDate;

    public BankAccountData getBankAccount()
    {
        return bankAccount;
    }

    public void setBankAccount(BankAccountData bankAccount)
    {
        this.bankAccount = bankAccount;
    }

    public String getFromTo()
    {
        return fromTo;
    }

    public void setFromTo(String fromTo)
    {
        this.fromTo = fromTo;
    }

    public BankAccountData getOtherBankAccount()
    {
        return otherBankAccount;
    }

    public void setOtherBankAccount(BankAccountData otherBankAccount)
    {
        this.otherBankAccount = otherBankAccount;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }
}
