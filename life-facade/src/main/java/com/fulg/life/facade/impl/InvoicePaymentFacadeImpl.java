package com.fulg.life.facade.impl;

import com.fulg.life.data.BankAccountMovementData;
import com.fulg.life.data.CategoryData;
import com.fulg.life.data.InvoiceData;
import com.fulg.life.data.InvoicePaymentData;
import com.fulg.life.facade.BankAccountMovementFacade;
import com.fulg.life.facade.CategoryFacade;
import com.fulg.life.facade.InvoicePaymentFacade;
import com.fulg.life.facade.converter.BankAccountMovementConverter;
import com.fulg.life.facade.converter.InvoiceConverter;
import com.fulg.life.facade.converter.reverse.BankAccountMovementReverseConverter;
import com.fulg.life.facade.converter.reverse.InvoiceReverseConverter;
import com.fulg.life.model.entities.BankAccountMovement;
import com.fulg.life.model.entities.Category;
import com.fulg.life.model.entities.Invoice;
import com.fulg.life.model.entities.User;
import com.fulg.life.service.BankAccountMovementService;
import com.fulg.life.service.InvoiceService;
import com.fulg.life.service.exception.UnauthorizedOperationException;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by alessandro.fulgenzi on 28/07/16.
 */
public class InvoicePaymentFacadeImpl implements InvoicePaymentFacade {
    private static Logger LOG = LoggerFactory.getLogger(CurrencyFacadeImpl.class);

    private static final String INVOICES_CATEGORY = "flastechinvoices";

    private static final Double ZERO = Double.valueOf(0d);
    private static final DateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");

    @Resource
    private BankAccountMovementFacade movementFacade;
    @Resource
    private CategoryFacade categoryFacade;
    @Resource
    private InvoiceService invoiceService;
    @Resource
    private InvoiceConverter invoiceConverter;
    @Resource
    private InvoiceReverseConverter invoiceReverseConverter;
    @Resource
    private BankAccountMovementFacade bankAccountMovementFacade;
    @Resource
    private BankAccountMovementService bankAccountMovementService;

    @Override
    public List<InvoicePaymentData> getAll(final User user)
    {
        final CategoryData categoryata = categoryFacade.getCategoryByCode(INVOICES_CATEGORY);

        final List<BankAccountMovementData> movements = movementFacade.getByCategory(categoryata.getPk(), user);
        final List<BankAccountMovementData> sortedMovements = Ordering.natural().onResultOf(
                new Function<BankAccountMovementData, String>() {
                    @Override
                    public String apply(BankAccountMovementData mov)
                    {
                        return mov.getDate().getTime() + "_" + mov.getPk();
                    }
                }).reverse().sortedCopy(movements);

        return buildResult(sortedMovements);
    }

    @Override
    public InvoicePaymentData save(InvoicePaymentData invoicePaymentData, User currentUser)
            throws UnauthorizedOperationException
    {
        // Save Movements
        final BankAccountMovementData savedMovement = bankAccountMovementFacade.update(invoicePaymentData.getMovement(), currentUser);

        // Save Invoices
        final List<InvoiceData> savedInvoices = Lists.newArrayList();
        for (final InvoiceData invoiceData : invoicePaymentData.getInvoices())
        {
            Invoice savedInvoice = invoiceService.save(invoiceReverseConverter.convert(invoiceData));
            List<BankAccountMovement> movements = bankAccountMovementService.getByInvoice(savedInvoice.getPk(), currentUser);
            savedInvoice = invoiceService.addOrUpdateMovement(savedInvoice, movements,
                    (BankAccountMovement) bankAccountMovementService.getOne(savedMovement.getPk()));

            savedInvoices.add(invoiceConverter.convert(savedInvoice));
        }

        invoicePaymentData.setInvoices(savedInvoices);
        invoicePaymentData.setMovement(savedMovement);
        return invoicePaymentData;
    }

    private List<InvoicePaymentData> buildResult(final List<BankAccountMovementData> movements)
    {
        final List<InvoicePaymentData> results = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(movements))
        {
            for (final BankAccountMovementData mov : movements)
            {
                final InvoicePaymentData invoicePayment = new InvoicePaymentData();
                invoicePayment.setMovement(mov);
                invoicePayment.setInvoices(getInvoices(mov));
                results.add(invoicePayment);
            }
        }
        return results;
    }

    private List<InvoiceData> getInvoices(final BankAccountMovementData movement)
    {
        final List<Invoice> invoices = invoiceService.getByMovement(movement.getPk());
        if (CollectionUtils.isEmpty(invoices))
        {
            return Lists.newArrayList(new InvoiceData());
        } else
        {
            return invoiceConverter.convertAll(invoices);
        }
    }

}
