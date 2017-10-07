package com.fulg.life.webmvc.controller;

import com.fulg.life.data.BankAccountData;
import com.fulg.life.data.MonthData;
import com.fulg.life.data.YearData;
import com.fulg.life.facade.BankAccountFacade;
import com.fulg.life.facade.DateTimeFacade;
import com.fulg.life.model.entities.User;
import com.fulg.life.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

public abstract class AbstractBaseController {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractBaseController.class);

	private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy - E";

	@Resource
	UserService userService;
	@Resource
	BankAccountFacade bankAccountFacade;
	@Resource
	DateTimeFacade dateTimeFacade;

	@ModelAttribute("contextPath")
	public String getContextPath(final HttpServletRequest request) {
		return request.getContextPath();
	}

	@ModelAttribute("resourcePath")
	public String getResourcePath(final HttpServletRequest request) {
		return request.getContextPath() + "/resources";
	}

	@ModelAttribute("defaultDateFormat")
	public String getDefaultDateFormat(final HttpServletRequest request) {
		return DEFAULT_DATE_FORMAT;
	}

	@ModelAttribute("bankAccounts")
	public List<BankAccountData> getBankAccounts(final HttpServletRequest request) {
		return bankAccountFacade.getAll(getCurrentUser(request));
	}

	@ModelAttribute("months")
	public List<MonthData> getMonths() {
		return dateTimeFacade.getMonths();
	}

	@ModelAttribute("years")
	public List<YearData> getYears() {
		return dateTimeFacade.getYears();
	}

	public void logHeaders(HttpServletRequest request) {
		LOG.info("----------HEADERS--------");
		for (Enumeration<String> headers = request.getHeaderNames(); headers.hasMoreElements();) {
			String headerName = headers.nextElement();
			LOG.info(headerName + ":" + request.getHeader(headerName));
		}
	}

	protected User getCurrentUser(HttpServletRequest request) {
		return userService.getCurrentUser(request.getSession().getId());
	}
}
