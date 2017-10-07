package com.fulg.life.facade.converter.reverse;

import com.fulg.life.data.InvoiceData;
import com.fulg.life.model.entities.Invoice;
import com.fulg.life.service.InvoiceService;
import com.fulg.life.service.FrequencyTypeService;

import javax.annotation.Resource;

/**
 * Created by alessandro.fulgenzi on 28/07/16.
 */
public class InvoiceReverseConverter extends AbstractReverseConverter<Invoice, InvoiceData> {
    @Resource
    InvoiceService InvoiceService;
    @Resource
    FrequencyTypeService frequencyTypeService;

    @Override
    protected Invoice createTarget(Long pk)
    {
        Invoice item = null;
        if (pk != null)
        {
            item = (Invoice) InvoiceService.getOne(pk);
        } else
        {
            item = new Invoice();
        }
        return item;
    }

    @Override
    public Invoice populate(InvoiceData source, Invoice target)
    {
        target.setPk(source.getPk());
        target.setMonth(source.getMonth());
        target.setDate(source.getDate());
        target.setDailyRate(source.getDailyRate());
        target.setWorkedDays(source.getDays());
        target.setDescription(source.getDescription());
        target.setNumber(source.getNumber());
        target.setSubTotal(target.getSubTotal());
        target.setTotal(source.getTotal());
        target.setVatRate(source.getVatRate());
        target.setYear(source.getYear());
        return target;
    }
}
