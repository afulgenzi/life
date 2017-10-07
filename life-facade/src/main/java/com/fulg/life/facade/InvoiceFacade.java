package com.fulg.life.facade;

import com.fulg.life.data.CategoryData;
import com.fulg.life.data.InvoiceData;
import com.fulg.life.model.entities.User;
import com.fulg.life.service.exception.UnauthorizedOperationException;

import java.util.List;

/**
 * Created by alessandro.fulgenzi on 26/07/16.
 */
public interface InvoiceFacade {
    List<InvoiceData> getAll(final User user);

    InvoiceData save(InvoiceData item)
            throws UnauthorizedOperationException;

//    void delete(InvoiceData item) throws UnauthorizedOperationException;
}
