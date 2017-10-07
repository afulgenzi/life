package com.fulg.life.model.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by alessandro.fulgenzi on 26/07/16.
 */
@Entity
@Table(name = "invoices")
public class Invoice extends Item {

    @Column(name = "NUMBER")
    private String number;

    @Column(name = "DATE")
    private Date date;

    @Column(name = "MONTH")
    private Integer month;

    @Column(name = "YEAR")
    private Integer year;

    @Column(name = "DAILY_RATE")
    private Double dailyRate;

    @Column(name = "WORKED_DAYS")
    private Double workedDays;

    @Column(name = "VAT_RATE")
    private Double vatRate;

    @Column(name = "SUBTOTAL")
    private Double subTotal;

    @Column(name = "TOTAL")
    private Double total;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToMany
    @JoinTable(
            name="invoicepayments",
            joinColumns=@JoinColumn(name="INVOICE_PK", referencedColumnName="PK"),
            inverseJoinColumns=@JoinColumn(name="MOVEMENT_PK", referencedColumnName="PK"))
    private List<BankAccountMovement> movements;

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

    public Double getWorkedDays()
    {
        return workedDays;
    }

    public void setWorkedDays(Double workedDays)
    {
        this.workedDays = workedDays;
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

    public List<BankAccountMovement> getMovements()
    {
        return movements;
    }

    public void setMovements(List<BankAccountMovement> movements)
    {
        this.movements = movements;
    }
}
