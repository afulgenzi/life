package com.fulg.life.webmvc.controller.json;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fulg.life.data.BankTransferData;
import com.fulg.life.facade.BankTransferFacade;
import com.fulg.life.facade.DateTimeFacade;
import com.fulg.life.service.exception.UnauthorizedOperationException;
import com.fulg.life.webmvc.controller.AbstractBaseController;

@Controller
@RequestMapping(value = "/transfer/json")
public class JsonTransferController extends AbstractBaseController {
	private static final Logger LOG = LoggerFactory.getLogger(JsonTransferController.class);

	@Resource
	BankTransferFacade bankTransferFacade;
	@Resource
	DateTimeFacade dateTimeFacade;

	@RequestMapping(value = "/addTransfer", method = RequestMethod.POST)
	@ResponseBody
	public BankTransferData add(HttpServletRequest request, HttpServletResponse response,
			@RequestBody final BankTransferData transfer) throws IOException {
		LOG.info("Creating Transfer fromMovement.date [{}], toMovement.date [{}]", transfer.getFromMovement().getDate(), transfer.getToMovement().getDate());
		try {
			LOG.info("CREATING TRANSFER for [{}] from [{}], to [{}]", transfer.getFromMovement().getAmount(), transfer.getFromBankAccount().getDisplayName(), transfer.getToBankAccount().getDisplayName());
			return bankTransferFacade.insert(transfer);
		} catch (UnauthorizedOperationException e) {
			throw new IOException(e);
		}
	}

	@RequestMapping(value = "/updateTransfer", method = RequestMethod.POST)
	@ResponseBody
	public BankTransferData save(HttpServletRequest request, HttpServletResponse response,
			@RequestBody final BankTransferData transfer) throws IOException {
		LOG.info("Updating Transfer fromMovement.date [{}], toMovement.date [{}]", transfer.getFromMovement().getDate(), transfer.getToMovement().getDate());
		try {
			LOG.info("UPDATING TRANSFER for [{}] from [{}], to [{}]", transfer.getFromMovement().getAmount(), transfer.getFromBankAccount().getDisplayName(), transfer.getToBankAccount().getDisplayName());
			BankTransferData returnTransfer = bankTransferFacade
					.update(transfer);
			return returnTransfer;
		} catch (UnauthorizedOperationException e) {
			throw new IOException(e);
		}
	}

	@RequestMapping(value = "/deleteTransfer", method = RequestMethod.POST)
	public void delete(HttpServletRequest request, HttpServletResponse response, @RequestBody final BankTransferData transfer)
			throws IOException {
		try {
			LOG.info("DELETING TRANSFER for [{}] from [{}], to [{}]", transfer.getFromMovement().getAmount(), transfer.getFromBankAccount().getDisplayName(), transfer.getToBankAccount().getDisplayName());
			bankTransferFacade.delete(transfer);
			return;
		} catch (UnauthorizedOperationException e) {
			throw new IOException(e);
		}
	}
}
