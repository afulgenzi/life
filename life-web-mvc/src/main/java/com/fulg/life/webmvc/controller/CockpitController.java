package com.fulg.life.webmvc.controller;

import com.fulg.life.data.*;
import com.fulg.life.facade.BankAccountFacade;
import com.fulg.life.facade.BankAccountMovementFacade;
import com.fulg.life.facade.DateTimeFacade;
import com.fulg.life.facade.ImportBankAccountMovementFacade;
import com.fulg.life.webmvc.controller.constant.ControllerConstants;
import com.fulg.life.webmvc.data.ImportForm;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/cockpit")
public class CockpitController extends AbstractBaseController {
	private static final Logger LOG = LoggerFactory.getLogger(CockpitController.class);

	@Resource
	BankAccountMovementFacade bankAccountMovementFacade;
	@Resource
	DateTimeFacade dateTimeFacade;
	@Resource
	ImportBankAccountMovementFacade importBankAccountMovementFacade;

	@RequestMapping(value = "/imports", method = RequestMethod.GET)
	public ModelAndView getCockpitImports(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DateData currentMonth = dateTimeFacade.getCurrentMonth();

		ModelAndView model = new ModelAndView(ControllerConstants.Views.COCKPIT_IMPORTS);

		model.addObject("currentMonth", currentMonth);

		return model;
	}

	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public ModelAndView getCockpitCategories(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DateData currentMonth = dateTimeFacade.getCurrentMonth();

		ModelAndView model = new ModelAndView(ControllerConstants.Views.COCKPIT_CATEGORIES);

		return model;
	}
}
