package com.fulg.life.webmvc.data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by alessandro.fulgenzi on 06/07/16.
 */
public class MultiAccountSearchByMonthForm {
    @NotNull(message = "{general.required}")
    private List<Long> bankAccounts;
    private Integer year;
    private Integer month;

    public List<Long> getBankAccounts()
    {
        return bankAccounts;
    }

    public void setBankAccounts(List<Long> bankAccounts)
    {
        this.bankAccounts = bankAccounts;
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
