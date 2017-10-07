package com.fulg.life.facade.converter;

import com.fulg.life.data.BankAccountMovementData;
import com.fulg.life.model.entities.BankAccountMovement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public class LloydsImportMovementFromTxtConverter extends AbstractImportBankAccountMovementConverter {
    private static final Logger LOG = LoggerFactory.getLogger(LloydsImportMovementFromTxtConverter.class);

    private final static String DATE_PATTERN = "dd MMM yy";
    private final static String AMOUNT_PATTERN = "¤#,###.00";

    @Override
    protected String getDatePattern() {
        return DATE_PATTERN;
    }

    @Override
    protected Locale getLocale() {
        return Locale.UK;
    }

    @Override
    protected String getAmountPattern() {
        return AMOUNT_PATTERN;
    }

    /**
     * Delimiter for TEXT from Lloyds is TAB
     */
    @Override
    protected String getFieldsDelimiter()
    {
        return "\t";
    }

    @Override
    public BankAccountMovementData populateInternal(final String[] source, BankAccountMovementData target) {
        for (int ind = 0; ind < source.length; ind++)
        {
            LOG.info("Field #{}: {}", ind, source[ind]);
        }
        target.setDate(getDate(source[0]));
        target.setDescription(source[1]);
        if (source[3] != null && (!"".equals(source[3].trim())))
        {
            target.setEu(BankAccountMovement.TYPE_IN);
            target.setAmount(getAmount(source[3]));
        } else
        {
            target.setEu(BankAccountMovement.TYPE_OUT);
            target.setAmount(getAmount(source[4]));
        }

        return target;
    }

}
