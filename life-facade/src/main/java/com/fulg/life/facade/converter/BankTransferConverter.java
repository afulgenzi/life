package com.fulg.life.facade.converter;

import com.fulg.life.data.BankTransferData;
import com.fulg.life.model.entities.BankTransfer;
import com.fulg.life.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class BankTransferConverter extends AbstractConverter<BankTransferData, BankTransfer> {
    private static final Logger LOG = LoggerFactory.getLogger(BankTransferConverter.class);

    @Resource
    private BankAccountConverter bankAccountConverter;

    @Override
    protected BankTransferData createTarget()
    {
        return new BankTransferData();
    }

    @Override
    public BankTransferData populate(BankTransfer source, BankTransferData target)
    {
        target.setPk(source.getPk());
        target.setFromBankAccount(bankAccountConverter.convert(source.getFromBankAccount()));
        target.setToBankAccount(bankAccountConverter.convert(source.getToBankAccount()));
//        target.setConversionRate(source.getConversionRate());
        target.setUseTargetCurrency(source.getUseTargetCurrency() == null ? Boolean.FALSE : source
                .getUseTargetCurrency());
        return target;
    }

}
