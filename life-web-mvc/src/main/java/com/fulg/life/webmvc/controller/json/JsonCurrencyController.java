package com.fulg.life.webmvc.controller.json;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fulg.life.data.CurrencyData;
import com.fulg.life.facade.CurrencyFacade;
import com.fulg.life.webmvc.controller.AbstractBaseController;

@Controller
@RequestMapping(value = "/currencies/json")
public class JsonCurrencyController extends AbstractBaseController {
	private static final Logger LOG = LoggerFactory.getLogger(JsonCurrencyController.class);

	@Resource
	CurrencyFacade currencyFacade;

	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<CurrencyData> getAll(HttpServletResponse response) throws IOException {
		List<CurrencyData> bankAccounts = currencyFacade.getAll();
		return bankAccounts;
	}

	@RequestMapping(value = "/getConversionRate", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Double getConversionRate(HttpServletResponse response,
			@RequestParam(value = "fromCurrency") final Long fromCurrency,
			@RequestParam(value = "toCurrency") final Long toCurrency) throws IOException {
		return currencyFacade.getConversionRate(fromCurrency, toCurrency);
	}
}
