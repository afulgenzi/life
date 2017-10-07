package com.fulg.life.facade.converter;

import com.fulg.life.data.BankAccountMovementData;
import com.fulg.life.data.BankTransferData;
import com.fulg.life.model.entities.BankAccountMovement;
import com.fulg.life.model.entities.BankTransfer;
import com.fulg.life.service.BankTransferService;
import com.fulg.life.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class BankAccountMovementConverter extends AbstractConverter<BankAccountMovementData, BankAccountMovement> {
	private static final Logger LOG = LoggerFactory.getLogger(BankAccountMovementConverter.class);

	@Resource
	private BankAccountConverter bankAccountConverter;
	@Resource
	private CurrencyConverter currencyConverter;
	@Resource
	private CategoryConverter categoryConverter;
	@Resource
	private CurrencyService currencyService;
	@Resource
	private BankTransferService bankTransferService;

	@Override
	protected BankAccountMovementData createTarget() {
		return new BankAccountMovementData();
	}

	@Override
	public BankAccountMovementData populate(BankAccountMovement source, BankAccountMovementData target) {
		populateBasic(source, target);
		return target;
	}

	public void populateBasic(BankAccountMovement source, BankAccountMovementData target) {
		LOG.debug("populateBasic -> pk [{}], description [{}]", source.getPk(), source.getDescription());
		target.setPk(source.getPk());
		target.setBalanceAfter(Double.valueOf(0.0)); // @TODO
		target.setBankAccount(bankAccountConverter.convert(source.getBankAccount()));
		target.setCurrency(currencyConverter.convert(source.getCurrency()));
		if (source.getCategory()!=null)
		{
			target.setCategory(categoryConverter.convert(source.getCategory()));
		}
		else
		{
			target.setCategory(null);
		}
		target.setDate(source.getDate());
		if (source.getBankTransfer() != null) {
			LOG.debug("SEARCHING FOR BANK TRANSFER DESCRIPTION FOR BANK TRANSFER [{}}", source.getBankTransfer().getPk());
			target.setDescription(bankTransferService.getBankTransferMovementDescription(source));
		} else {
			target.setDescription(source.getDescription());
		}
		target.setEu(source.getEu());
		target.setFinancialDuty(null); // @TODO
		if (source.getBankTransfer() != null) {
			// Assign ONLY target movement, otherwise there would be a loop in
			// converting both of them
			BankTransfer transfer = source.getBankTransfer();
			if (transfer.getFromMovement().equals(source)) {
				target.setTransferTargetMovement(this.convert(transfer.getToMovement()));
			}
			final BankTransferData transferData = new BankTransferData();
			transferData.setPk(transfer.getPk());
			target.setBankTransfer(transferData);
		}
		if (!source.getCurrency().getCode().equals(source.getBankAccount().getCurrency().getCode())) {
			LOG.info("Trying to convert amount from " + source.getCurrency().getCode() + " to "
					+ source.getBankAccount().getCurrency().getCode());
			// Source BankAccount in EURO
			if (source.getBankAccount().getCurrency().getCode().equals(currencyService.getEuro().getCode())) {
				LOG.info("Converted amount from " + source.getCurrency().getCode() + " to "
						+ source.getBankAccount().getCurrency().getCode());
				target.setAmount(source.getAmount() * source.getCurrency().getAmountInEuro());
			} else {
				target.setAmount(source.getAmount());
			}
		} else {
			target.setAmount(source.getAmount());
		}
	}

}
