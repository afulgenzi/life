package com.fulg.life.facade;

import com.fulg.life.data.CategoryData;
import com.fulg.life.data.InvoiceData;
import com.fulg.life.data.InvoicePaymentData;
import com.fulg.life.model.entities.User;
import com.fulg.life.service.exception.UnauthorizedOperationException;

import java.util.List;

/**
 * Created by alessandro.fulgenzi on 26/07/16.
 */
public interface InvoicePaymentFacade {
    List<InvoicePaymentData> getAll(final User user);

    InvoicePaymentData save(InvoicePaymentData invoicePaymentData, User currentUser)
            throws UnauthorizedOperationException;
}
