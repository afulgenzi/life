package com.fulg.life.facade.impl;

import com.fulg.life.data.InvoiceData;
import com.fulg.life.facade.BankAccountMovementFacade;
import com.fulg.life.facade.CategoryFacade;
import com.fulg.life.facade.InvoiceFacade;
import com.fulg.life.facade.converter.InvoiceConverter;
import com.fulg.life.facade.converter.reverse.InvoiceReverseConverter;
import com.fulg.life.model.entities.Invoice;
import com.fulg.life.model.entities.User;
import com.fulg.life.service.InvoiceService;
import com.fulg.life.service.exception.UnauthorizedOperationException;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by alessandro.fulgenzi on 26/07/16.
 */
public class InvoiceFacadeImpl implements InvoiceFacade {
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

    @Override
    public List<InvoiceData> getAll(final User user)
    {
//        final CategoryData categoryata = categoryFacade.getCategoryByCode(INVOICES_CATEGORY);
//
//        final List<BankAccountMovementData> movements = movementFacade.getByCategory(categoryata.getPk(), user);
//        final List<BankAccountMovementData> sortedMovements = Ordering.natural().onResultOf(
//                new Function<BankAccountMovementData, String>() {
//                    @Override
//                    public String apply(BankAccountMovementData mov)
//                    {
//                        return mov.getDate().getTime() + "_" + mov.getPk();
//                    }
//                }).reverse().sortedCopy(movements);

        return Lists.newArrayList();
    }

    @Override
    public InvoiceData save(InvoiceData invoiceData)
            throws UnauthorizedOperationException
    {
        Invoice item = invoiceReverseConverter.convert(invoiceData);
        item = invoiceService.save(item);
        return invoiceConverter.convert(item);
    }

//    private List<InvoiceData> buildResult(final List<BankAccountMovementData> movements)
//    {
//        final List<InvoiceData> results = Lists.newArrayList();
//        if (CollectionUtils.isNotEmpty(movements))
//        {
//            for (final BankAccountMovementData mov : movements)
//            {
//                final List<InvoiceData> invoiceData = getInvoiceData(mov.getPk());
//                invoiceData.getMovements().add(mov);
//                results.add(invoiceData);
//            }
//        }
//        return results;
//    }

    private List<InvoiceData> getInvoiceData(final Long movementPk)
    {
        final List<Invoice> invoices = invoiceService.getByMovement(movementPk);
        if (CollectionUtils.isEmpty(invoices))
        {
            return Lists.newArrayList(new InvoiceData());
        } else
        {
            return invoiceConverter.convertAll(invoices);
        }
    }
}
