package com.fulg.life.webmvc.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulg.life.data.*;
import com.fulg.life.facade.BankAccountFacade;
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

import com.fulg.life.facade.BankAccountMovementFacade;
import com.fulg.life.facade.DateTimeFacade;

@Controller
@RequestMapping(value = "/movements")
public class MovementController extends AbstractBaseController {
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
	public ModelAndView getAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DateData currentMonth = dateTimeFacade.getCurrentMonth();
		return searchByMonth(request, currentMonth.getYear().getCode(), currentMonth.getMonth().getCode());
	}

	@RequestMapping(value = "/currentMonth", method = RequestMethod.GET)
	public ModelAndView getCurrentMonth(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DateData currentMonth = dateTimeFacade.getCurrentMonth();
		return searchByMonth(request, currentMonth.getYear().getCode(), currentMonth.getMonth().getCode());
	}

	@RequestMapping(value = "/import", method = RequestMethod.GET)
	public ModelAndView getImport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return prepareImport(null, null, request);
	}

	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public ModelAndView submitImport(final ImportForm importForm, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<ImportBankMovementData> movements = importBankAccountMovementFacade.importMovementsFromText(importForm.getBankAccount().getPk(), importForm.getPayload(), getCurrentUser(request));
		return prepareImport(importForm, movements, request);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "month") final Integer month,
			@RequestParam(value = "year") final Integer year) throws IOException {
		return searchByMonth(request, year, month);
	}

	@ModelAttribute("pageType")
	public String getPageType() {
		return ControllerConstants.PageTypes.PAGE_TYPE_TRANSACTIONS;
	}

	public ModelAndView searchByMonth(HttpServletRequest request, int year, int month) throws IOException {
		ModelAndView model = new ModelAndView(ControllerConstants.Views.MOVEMENTS);
//		model.addObject("pageType", PAGE_TYPE_TRANSACTIONS);

		DateData currentMonth = dateTimeFacade.getMonth(year, month);
		DateData prevMonth = dateTimeFacade.getPreviousMonth(year, month);
		DateData nextMonth = dateTimeFacade.getNextMonth(year, month);
		model.addObject("currentMonth", currentMonth);
		model.addObject("prevMonth", prevMonth);
		model.addObject("nextMonth", nextMonth);

		return model;
	}

	protected ModelAndView prepareImport(final ImportForm inputImportForm, final List<ImportBankMovementData> importMovements, final HttpServletRequest request){
		final ModelAndView model = new ModelAndView(ControllerConstants.Views.MOVEMENTS_IMPORT);

		final ImportForm importForm = new ImportForm();
		if (inputImportForm != null)
		{
			importForm.setBankAccount(inputImportForm.getBankAccount());
			importForm.setPayload(inputImportForm.getPayload());

			final BankAccountData bankAccount = bankAccountFacade.getById(importForm.getBankAccount().getPk(), getCurrentUser(request));
			model.addObject("bankAccount", bankAccount);
		}
		model.addObject(importForm);

		if (importMovements != null)
		{
			final String jsonMovements = new Gson().toJson(importMovements);
			LOG.info("jsonMovements : {}", jsonMovements);
			model.addObject("movementsJson", jsonMovements);
			model.addObject("movements", importMovements);
		}

		return model;
	}

	void setView(ModelAndView model) {
		model.setViewName(ControllerConstants.Views.MOVEMENTS);
	}

}
