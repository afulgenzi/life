package com.fulg.life.webmvc.data;

/**
 * Created by alessandro.fulgenzi on 22/07/16.
 */

import com.fulg.life.data.BankAccountData;
import com.fulg.life.data.CategoryData;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by alessandro.fulgenzi on 10/02/16.
 */
public class MultiAccountSearchForm
{
    @NotNull(message = "{general.required}")
    private List<Long> bankAccounts;
    private String description;
    private CategoryData category;
    private Date startDate;
    private Date endDate;
    private String inOut;

    public List<Long> getBankAccounts()
    {
        return bankAccounts;
    }

    public void setBankAccounts(List<Long> bankAccounts)
    {
        this.bankAccounts = bankAccounts;
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
}
