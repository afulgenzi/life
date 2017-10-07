package com.fulg.life.facade.converter;

import com.fulg.life.data.InvoiceData;
import com.fulg.life.model.entities.Invoice;

/**
 * Created by alessandro.fulgenzi on 28/07/16.
 */
public class InvoiceConverter extends AbstractConverter<InvoiceData, Invoice> {

    @Override
    protected InvoiceData createTarget()
    {
        return new InvoiceData();
    }

    @Override
    public InvoiceData populate(Invoice source, InvoiceData target)
    {
        if (source != null)
        {
            target.setPk(source.getPk());
            target.setMonth(source.getMonth());
            target.setDate(source.getDate());
            target.setDailyRate(source.getDailyRate());
            target.setDays(source.getWorkedDays());
            target.setDescription(source.getDescription());
            target.setNumber(source.getNumber());
            target.setSubTotal(target.getSubTotal());
            target.setTotal(source.getTotal());
            target.setVatRate(source.getVatRate());
            target.setYear(source.getYear());
        }
        return target;
    }
}
