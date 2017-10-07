package com.fulg.life.webmvc.controller.json;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulg.life.webmvc.controller.constant.ControllerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fulg.life.data.BankAccountData;
import com.fulg.life.facade.BankAccountFacade;
import com.fulg.life.webmvc.controller.AbstractBaseController;

@Controller
@RequestMapping(value = ControllerConstants.PageUrls.Json.BANK_ACCOUNTS)
public class JsonBankAccountController extends AbstractBaseController {
	private static final Logger LOG = LoggerFactory.getLogger(JsonBankAccountController.class);

	@Resource
	BankAccountFacade bankAccountFacade;

	@RequestMapping(value = "/getBankAccounts", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<BankAccountData> getBankAccounts(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<BankAccountData> bankAccounts = bankAccountFacade.getAll(getCurrentUser(request));
		return bankAccounts;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public BankAccountData add(HttpServletRequest request, HttpServletResponse response,
			@RequestBody final BankAccountData account) throws IOException {
		LOG.info("CREATING BANK ACCOUNT " + account.getAccountNumber());
		BankAccountData returnMovement = bankAccountFacade.insert(account);
		return returnMovement;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BankAccountData save(HttpServletRequest request, HttpServletResponse response,
			@RequestBody final BankAccountData account) throws IOException {
		LOG.info("UPDATING BANK ACCOUNT " + account.getAccountNumber());
		BankAccountData returnMovement = bankAccountFacade.update(account);
		return returnMovement;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(HttpServletResponse response, @RequestBody final BankAccountData account)
			throws IOException {
		LOG.info("UPDATING BANK ACCOUNT " + account.getAccountNumber());
		bankAccountFacade.delete(account);
		return;
	}
}
