package com.fulg.life.data;

import java.util.Date;

/**
 * Created by alessandro.fulgenzi on 26/07/16.
 */
public class InvoiceData extends ItemData {
    private String number;
    private Date date;
    private Integer month;
    private Integer year;
    private Double dailyRate;
    private Double days;
    private Double vatRate = Double.valueOf(20d);
    private Double subTotal;
    private Double total;
    private String description;

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public Integer getMonth()
    {
        return month;
    }

    public void setMonth(Integer month)
    {
        this.month = month;
    }

    public Integer getYear()
    {
        return year;
    }

    public void setYear(Integer year)
    {
        this.year = year;
    }

    public Double getDailyRate()
    {
        return dailyRate;
    }

    public void setDailyRate(Double dailyRate)
    {
        this.dailyRate = dailyRate;
    }

    public Double getDays()
    {
        return days;
    }

    public void setDays(Double days)
    {
        this.days = days;
    }

    public Double getVatRate()
    {
        return vatRate;
    }

    public void setVatRate(Double vatRate)
    {
        this.vatRate = vatRate;
    }

    public Double getSubTotal()
    {
        return subTotal;
    }

    public void setSubTotal(Double subTotal)
    {
        this.subTotal = subTotal;
    }

    public Double getTotal()
    {
        return total;
    }

    public void setTotal(Double total)
    {
        this.total = total;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
