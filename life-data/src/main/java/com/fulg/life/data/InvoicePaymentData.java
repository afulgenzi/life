package com.fulg.life.data;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by alessandro.fulgenzi on 28/07/16.
 */
public class InvoicePaymentData {
    private List<InvoiceData> invoices = Lists.newArrayList();
    private BankAccountMovementData movement;

    public List<InvoiceData> getInvoices()
    {
        return invoices;
    }

    public void setInvoices(List<InvoiceData> invoices)
    {
        this.invoices = invoices;
    }

    public BankAccountMovementData getMovement()
    {
        return movement;
    }

    public void setMovement(BankAccountMovementData movement)
    {
        this.movement = movement;
    }
}
