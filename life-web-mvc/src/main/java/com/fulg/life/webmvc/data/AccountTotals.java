package com.fulg.life.webmvc.data;

import com.fulg.life.data.BankAccountData;

/**
 * Created by alessandro.fulgenzi on 28/06/16.
 */
public class AccountTotals {
    private BankAccountData account;
    private Double amountIn = 0d;
    private Double amountOut = 0d;
    private String formattedAmountIn;
    private String formattedAmountOut;

    public BankAccountData getAccount()
    {
        return account;
    }

    public void setAccount(BankAccountData account)
    {
        this.account = account;
    }

    public Double getAmountIn()
    {
        return amountIn;
    }

    public void setAmountIn(Double amountIn)
    {
        this.amountIn = amountIn;
    }

    public Double getAmountOut()
    {
        return amountOut;
    }

    public void setAmountOut(Double amountOut)
    {
        this.amountOut = amountOut;
    }

    public String getFormattedAmountIn()
    {
        return formattedAmountIn;
    }

    public void setFormattedAmountIn(String formattedAmountIn)
    {
        this.formattedAmountIn = formattedAmountIn;
    }

    public String getFormattedAmountOut()
    {
        return formattedAmountOut;
    }

    public void setFormattedAmountOut(String formattedAmountOut)
    {
        this.formattedAmountOut = formattedAmountOut;
    }
}
