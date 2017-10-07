package com.fulg.life.webmvc.data;

import com.fulg.life.data.InvoiceData;

import java.math.BigDecimal;

/**
 * Created by alessandro.fulgenzi on 26/07/16.
 */
public class InvoiceResult {
    private InvoiceData invoice = null;
    private BigDecimal movementsTotal = BigDecimal.ZERO;
    private String formattedMovementsTotal = "";

    public InvoiceData getInvoice()
    {
        return invoice;
    }

    public void setInvoice(InvoiceData invoice)
    {
        this.invoice = invoice;
    }

    public BigDecimal getMovementsTotal()
    {
        return movementsTotal;
    }

    public void setMovementsTotal(BigDecimal movementsTotal)
    {
        this.movementsTotal = movementsTotal;
    }

    public String getFormattedMovementsTotal()
    {
        return formattedMovementsTotal;
    }

    public void setFormattedMovementsTotal(String formattedMovementsTotal)
    {
        this.formattedMovementsTotal = formattedMovementsTotal;
    }
}
