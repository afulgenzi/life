package com.fulg.life.webmvc.controller.json;

import com.fulg.life.data.*;
import com.fulg.life.data.util.BigDecimalUtils;
import com.fulg.life.data.util.CurrencyUtils;
import com.fulg.life.facade.InvoiceFacade;
import com.fulg.life.facade.InvoicePaymentFacade;
import com.fulg.life.facade.converter.CurrencyConverter;
import com.fulg.life.service.CurrencyService;
import com.fulg.life.service.exception.UnauthorizedOperationException;
import com.fulg.life.webmvc.controller.AbstractBaseController;
import com.fulg.life.webmvc.controller.json.data.JsonError;
import com.fulg.life.webmvc.controller.json.data.JsonResult;
import com.fulg.life.webmvc.controller.json.data.JsonSuccess;
import com.fulg.life.webmvc.data.CategoryForm;
import com.fulg.life.webmvc.data.InvoiceResult;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by alessandro.fulgenzi on 26/07/16.
 */
@Controller
@RequestMapping(value = "/invoices/json")
public class JsonInvoiceController extends AbstractBaseController {

    @Resource
    private InvoicePaymentFacade invoicePaymentFacade;
    @Resource
    private CurrencyService currencyService;
    @Resource
    private CurrencyConverter currencyConverter;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<List<InvoicePaymentData>> getAll(HttpServletRequest request
            , HttpServletResponse response) throws IOException
    {
        final List<InvoicePaymentData> invoicePayments = invoicePaymentFacade.getAll(getCurrentUser(request));

        return new JsonSuccess<List<InvoicePaymentData>>(invoicePayments);
    }

    @RequestMapping(value = "/insertUpdateInvoicePayment", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public JsonResult<InvoicePaymentData> insertUpdateInvoicePayment(HttpServletRequest request,
                                                         @RequestBody final InvoicePaymentData invoicePaymentData) throws UnauthorizedOperationException
    {
        if (invoicePaymentData == null){
            return new JsonError<InvoicePaymentData>("Missing Invoice Payment info.");
        }

        // Invoice validation
        if (CollectionUtils.isEmpty(invoicePaymentData.getInvoices())){
            return new JsonError<InvoicePaymentData>("Missing Invoice data.");
//        }else{
//            final InvoiceData invoice = invoicePaymentData.getInvoices().get(0);
//            if (invoice.getDailyRate() == null){
//                return new JsonError<InvoicePaymentData>("Missing Invoice info: Daily Rate");
//            }
        }

        // Transaction validation
        if (invoicePaymentData.getMovement() == null){
            return new JsonError<InvoicePaymentData>("Missing Transaction data.");
        }

        final InvoicePaymentData resultInvoicePaymentData = invoicePaymentFacade.save(invoicePaymentData, getCurrentUser(request));

        return new JsonSuccess<InvoicePaymentData>(resultInvoicePaymentData);
    }
}
