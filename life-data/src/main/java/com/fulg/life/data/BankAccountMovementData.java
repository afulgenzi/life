package com.fulg.life.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.fulg.life.data.util.CurrencyUtils;

public class BankAccountMovementData extends ItemData implements Comparable<BankAccountMovementData> {
    private static final long serialVersionUID = 1L;
    public static final String IN = "E";
    public static final String OUT = "U";
    private static final String CURRENCY_PATTERN = "0.00";

    BankAccountData bankAccount;
    FinancialDutyData financialDuty;
    Double amount;
    String formattedAmount;
    Date date;
    String description;
    String eu;
    CurrencyData currency;
    CategoryData category;
    String checked;
    Double balanceAfter;
    Double overallBalanceAfter;
    String formattedBalanceAfter;
    String formattedOverallBalanceAfter;
    List<BankAccountBalanceData> bankAccountBalances = new ArrayList<BankAccountBalanceData>();
    BankAccountMovementData transferTargetMovement;
    BankAccountMovementData transferSourceMovement;
    BankTransferData bankTransfer;

	public BankAccountData getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountData bankAccount) {
        this.bankAccount = bankAccount;
    }

    public FinancialDutyData getFinancialDuty() {
        return financialDuty;
    }

    public void setFinancialDuty(FinancialDutyData financialDuty) {
        this.financialDuty = financialDuty;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
        formatAmount();
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

    public Double getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(Double balanceAfter) {
        this.balanceAfter = balanceAfter;
        if (currency != null) {
            if (balanceAfter != null) {
                setFormattedBalanceAfter(CurrencyUtils.formatDouble(balanceAfter, currency, CURRENCY_PATTERN));
            }
        }
    }

    public Double getOverallBalanceAfter() {
        return overallBalanceAfter;
    }

    public void setOverallBalanceAfter(Double overallBalanceAfter, CurrencyData forcedCurrency) {
        this.overallBalanceAfter = overallBalanceAfter;
        if (forcedCurrency != null)
        {
            if (overallBalanceAfter != null)
            {
                setFormattedOverallBalanceAfter(
                        CurrencyUtils.formatDouble(overallBalanceAfter, forcedCurrency, CURRENCY_PATTERN));
            }
        } else if (currency != null)
        {
            if (overallBalanceAfter != null)
            {
                setFormattedOverallBalanceAfter(
                        CurrencyUtils.formatDouble(overallBalanceAfter, currency, CURRENCY_PATTERN));
            }
        }
    }

    public String getEu() {
        return eu;
    }

    public void setEu(String eu) {
        this.eu = eu;
    }

    public CurrencyData getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyData currency) {
        this.currency = currency;
        formatAmount();
    }

    public CategoryData getCategory() {
        return category;
    }

    public void setCategory(CategoryData category) {
        this.category = category;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public List<BankAccountBalanceData> getBankAccountBalances() {
        return bankAccountBalances;
    }

    public void setBankAccountBalances(List<BankAccountBalanceData> bankAccountBalances) {
        this.bankAccountBalances = bankAccountBalances;
    }

    public BankAccountMovementData getTransferTargetMovement() {
        return transferTargetMovement;
    }

    public void setTransferTargetMovement(BankAccountMovementData transferTargetMovement) {
        this.transferTargetMovement = transferTargetMovement;
    }

    public BankAccountMovementData getTransferSourceMovement() {
        return transferSourceMovement;
    }

    public void setTransferSourceMovement(BankAccountMovementData transferSourceMovement) {
        this.transferSourceMovement = transferSourceMovement;
    }

    public BankTransferData getBankTransfer() {
        return bankTransfer;
    }

    public void setBankTransfer(BankTransferData bankTransfer) {
        this.bankTransfer = bankTransfer;
    }

    public String getFormattedAmount() {
        return formattedAmount;
    }

    public void setFormattedAmount(String formattedAmount) {
        this.formattedAmount = formattedAmount;
    }

    public String getFormattedBalanceAfter() {
        return formattedBalanceAfter;
    }

    public void setFormattedBalanceAfter(String formattedBalanceAfter) {
        this.formattedBalanceAfter = formattedBalanceAfter;
    }

    public String getFormattedOverallBalanceAfter() {
        return formattedOverallBalanceAfter;
    }

    public void setFormattedOverallBalanceAfter(String formattedOverallBalanceAfter) {
        this.formattedOverallBalanceAfter = formattedOverallBalanceAfter;
    }

    public int compareTo(BankAccountMovementData o) {
        BankAccountMovementData movement = (BankAccountMovementData) o;
        if (this.date.before(movement.getDate())) {
            return -1;
        } else if (this.date.after(movement.getDate())) {
            return 1;
        } else {
            if (this.pk < movement.getPk()) {
                return -1;
            } else if (this.pk > movement.getPk()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public void formatAmount() {
        if (amount != null && currency != null) {
            setFormattedAmount(CurrencyUtils.formatDouble(amount, currency, CURRENCY_PATTERN));
        }
    }
}
