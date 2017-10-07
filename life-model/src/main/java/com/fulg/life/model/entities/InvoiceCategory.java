package com.fulg.life.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by alessandro.fulgenzi on 30/06/16.
 */
@Entity
@Table(name = "invoicecategories")
public class InvoiceCategory extends Category {
    @Column(name = "DATE_FROM")
    Date dateFrom;

    @Column(name = "DATE_TO")
    Date dateTo;

    @Column(name = "DUE_DATE")
    Date dueDate;

    @Column(name = "DAYS")
    Integer days;

    @Column(name = "DAILY_RATE")
    Double dailyRate;

    @Column(name = "SUBTOTAL")
    String subTotal;

    @Column(name = "VAT")
    String vat;

    @Column(name = "TOTAL")
    String total;

    public Date getDateFrom()
    {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom)
    {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo()
    {
        return dateTo;
    }

    public void setDateTo(Date dateTo)
    {
        this.dateTo = dateTo;
    }

    public Date getDueDate()
    {
        return dueDate;
    }

    public void setDueDate(Date dueDate)
    {
        this.dueDate = dueDate;
    }

    public Integer getDays()
    {
        return days;
    }

    public void setDays(Integer days)
    {
        this.days = days;
    }

    public Double getDailyRate()
    {
        return dailyRate;
    }

    public void setDailyRate(Double dailyRate)
    {
        this.dailyRate = dailyRate;
    }

    public String getSubTotal()
    {
        return subTotal;
    }

    public void setSubTotal(String subTotal)
    {
        this.subTotal = subTotal;
    }

    public String getVat()
    {
        return vat;
    }

    public void setVat(String vat)
    {
        this.vat = vat;
    }

    public String getTotal()
    {
        return total;
    }

    public void setTotal(String total)
    {
        this.total = total;
    }
}
