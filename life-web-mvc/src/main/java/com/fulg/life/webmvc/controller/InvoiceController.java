package com.fulg.life.webmvc.controller;

import com.fulg.life.facade.BankAccountFacade;
import com.fulg.life.facade.BankAccountMovementFacade;
import com.fulg.life.facade.DateTimeFacade;
import com.fulg.life.webmvc.controller.constant.ControllerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by alessandro.fulgenzi on 25/07/16.
 */
@Controller
@RequestMapping(value = "/invoices")
public class InvoiceController extends AbstractBaseController {
    private static final Logger LOG = LoggerFactory.getLogger(InvoiceController.class);

    @Resource
    BankAccountMovementFacade bankAccountMovementFacade;
    @Resource
    DateTimeFacade dateTimeFacade;
    @Resource
    BankAccountFacade bankAccountFacade;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getList(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        return prepareResult(request);
    }

    protected ModelAndView prepareResult(final HttpServletRequest request)
    {
        final ModelAndView model = new ModelAndView(ControllerConstants.Views.INVOICES);

//        final ImportForm importForm = new ImportForm();
//        if (inputImportForm != null)
//        {
//            importForm.setBankAccount(inputImportForm.getBankAccount());
//            importForm.setPayload(inputImportForm.getPayload());
//
//            final BankAccountData bankAccount = bankAccountFacade.getById(importForm.getBankAccount().getPk(),
//                    getCurrentUser(request));
//            model.addObject("bankAccount", bankAccount);
//        }
//        model.addObject(importForm);
//
//        if (importMovements != null)
//        {
//            final String jsonMovements = new Gson().toJson(importMovements);
//            LOG.info("jsonMovements : {}", jsonMovements);
//            model.addObject("movementsJson", jsonMovements);
//            model.addObject("movements", importMovements);
//        }

        return model;
    }

    @ModelAttribute("pageType")
    public String getPageType() {
        return ControllerConstants.PageTypes.PAGE_TYPE_INVOICES;
    }
}
