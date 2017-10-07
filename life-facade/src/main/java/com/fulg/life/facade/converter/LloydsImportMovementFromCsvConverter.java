package com.fulg.life.facade.converter;

import com.fulg.life.data.BankAccountMovementData;
import com.fulg.life.model.entities.BankAccountMovement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public class LloydsImportMovementFromCsvConverter extends AbstractImportBankAccountMovementConverter {
    private static final Logger LOG = LoggerFactory.getLogger(LloydsImportMovementFromCsvConverter.class);

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

    @Override
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
        target.setDate(getDate(source[0]));
        target.setDescription(source[4]);
        if (source[6] != null && (!"".equals(source[6].trim())))
        {
            target.setEu(BankAccountMovement.TYPE_IN);
            target.setAmount(Math.abs(getAmount(source[6])));
        } else
        {
            target.setEu(BankAccountMovement.TYPE_OUT);
            target.setAmount(Math.abs(getAmount(source[5])));
        }

        return target;
    }

}
