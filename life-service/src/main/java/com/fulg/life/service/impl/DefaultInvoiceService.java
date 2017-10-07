package com.fulg.life.service.impl;

import com.fulg.life.model.entities.BankAccountMovement;
import com.fulg.life.model.entities.Invoice;
import com.fulg.life.model.entities.Item;
import com.fulg.life.model.repository.InvoiceRepository;
import com.fulg.life.service.InvoiceService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by alessandro.fulgenzi on 28/07/16.
 */
@Service
public class DefaultInvoiceService implements InvoiceService {
    @SuppressWarnings("unused")
    private static Logger LOG = LoggerFactory.getLogger(DefaultInvoiceService.class);

    @Resource
    InvoiceRepository invoiceRepository;


    @Override
    public List<Invoice> getByMovement(Long movementPk)
    {
        return invoiceRepository.findByMovement(movementPk);
    }

    @Override
    public Item getOne(Long pk)
    {
        return invoiceRepository.findOne(pk);
    }

    @Override
    public Invoice save(Invoice item)
    {
        invoiceRepository.saveAndFlush(item);
        return item;
    }

    @Override
    public Invoice addOrUpdateMovement(final Invoice invoice, List<BankAccountMovement> movements, final BankAccountMovement movement)
    {
        invoice.setMovements(addOrReplaceItem(movements, movement));
        return save(invoice);
    }

    @Override
    public void delete(Invoice item)
    {
        invoiceRepository.delete(item);
    }

    private List<BankAccountMovement> addOrReplaceItem(final List<BankAccountMovement> existingList, final BankAccountMovement newItem){
        List<BankAccountMovement> newList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(existingList)){
            for (final BankAccountMovement mov:existingList){
                if (!mov.getPk().equals(newItem.getPk())){
                    newList.add(mov);
                }
            }
        }
        newList.add(newItem);
        return newList;
    }

}
