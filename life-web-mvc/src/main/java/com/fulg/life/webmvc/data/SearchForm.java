package com.fulg.life.webmvc.data;

import javax.validation.constraints.NotNull;

import com.fulg.life.data.BankAccountData;
import com.fulg.life.data.CategoryData;

import java.util.Date;
import java.util.List;

/**
 * Created by alessandro.fulgenzi on 10/02/16.
 */
public class SearchForm
{
    @NotNull(message = "{general.required}")
    private BankAccountData bankAccount;
    private String description;
    private CategoryData category;
    private Date startDate;
    private Date endDate;
    private String inOut;
    private Boolean uncategorised;

    public BankAccountData getBankAccount()
    {
        return bankAccount;
    }

    public void setBankAccount(BankAccountData bankAccount)
    {
        this.bankAccount = bankAccount;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public CategoryData getCategory()
    {
        return category;
    }

    public void setCategory(CategoryData category)
    {
        this.category = category;
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

    public String getInOut()
    {
        return inOut;
    }

    public void setInOut(String inOut)
    {
        this.inOut = inOut;
    }

    public Boolean getUncategorised()
    {
        return uncategorised;
    }

    public void setUncategorised(Boolean uncategorised)
    {
        this.uncategorised = uncategorised;
    }
}
