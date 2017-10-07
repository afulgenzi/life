package com.fulg.life.webmvc.controller;

import com.fulg.life.facade.BankAccountFacade;
import com.fulg.life.facade.BankAccountMovementFacade;
import com.fulg.life.facade.DateTimeFacade;
import com.fulg.life.facade.ImportBankAccountMovementFacade;
import com.fulg.life.webmvc.controller.constant.ControllerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by alessandro.fulgenzi on 13/07/16.
 */
@Controller
@RequestMapping(value = "/categories")
public class CategoryController extends AbstractBaseController {
    private static final Logger LOG = LoggerFactory.getLogger(MovementController.class);

    @Resource
    BankAccountMovementFacade bankAccountMovementFacade;
    @Resource
    DateTimeFacade dateTimeFacade;
    @Resource
    ImportBankAccountMovementFacade importBankAccountMovementFacade;
    @Resource
    BankAccountFacade bankAccountFacade;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getAll(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        ModelAndView model = new ModelAndView(ControllerConstants.Views.CATEGORIES);

        return model;
    }
}
