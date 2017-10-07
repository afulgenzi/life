package com.fulg.life.facade.converter;

import com.fulg.life.data.BankAccountMovementData;
import com.fulg.life.model.entities.BankAccountMovement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public class BarclaysImportMovementFromCsvConverter extends AbstractImportBankAccountMovementConverter {
    private static final Logger LOG = LoggerFactory.getLogger(BarclaysImportMovementFromCsvConverter.class);

    private final static String DATE_PATTERN = "dd/MM/yyyy";
    private final static String AMOUNT_PATTERN = "#,###.##";

    @Override
    protected String getDatePattern() {
        return DATE_PATTERN;
    }

    @Override
    protected Locale getLocale() {
        return Locale.UK;
    }

    protected String getFieldsDelimiter()
    {
    	return ",";
    }

    @Override
    protected String getAmountPattern() {
        return AMOUNT_PATTERN;
    }

    @Override
    public BankAccountMovementData populateInternal(String[] source, BankAccountMovementData target) {
        for (int ind = 0; ind < source.length; ind++)
        {
            LOG.debug("Field #{}: {}", ind, source[ind]);
        }
        target.setDate(getDate(source[1]));
        target.setDescription(source[5]);
        final double relativeAmount = getAmount(source[3]);
        target.setAmount(Math.abs(relativeAmount));
        if (relativeAmount >= 0)
        {
            target.setEu(BankAccountMovement.TYPE_IN);
        } else
        {
            target.setEu(BankAccountMovement.TYPE_OUT);
        }

        return target;
    }

}
