package com.fulg.life.facade.converter;

import com.fulg.life.data.BankAccountMovementData;
import com.fulg.life.facade.data.ImportChunk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.text.*;
import java.util.Date;
import java.util.Locale;

public abstract class AbstractImportBankAccountMovementConverter extends AbstractConverter<BankAccountMovementData, ImportChunk> {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractImportBankAccountMovementConverter.class);

    @Override
    protected BankAccountMovementData createTarget() {
        return new BankAccountMovementData();
    }

    @Override
    public BankAccountMovementData populate(ImportChunk source, BankAccountMovementData target) {
        return populateInternal(split(source), target);
    }

    protected Date getDate(final String source){
        final SimpleDateFormat formatter = new SimpleDateFormat(getDatePattern());
        try
        {
            return formatter.parse(source);
        } catch (ParseException e)
        {
            String msg = "Could't parse [" + source + "] using locale [" + getDatePattern() + "]";
            LOG.info(msg);
            throw new MovementParseException(msg);
        }
    }

    protected Double getAmount(final String source){
        try
        {
            NumberFormat numberFormat = new DecimalFormat(getAmountPattern(), new DecimalFormatSymbols(getLocale()));

            return Double.valueOf(numberFormat.parse(source).doubleValue());
        } catch (ParseException e)
        {
            String msg = "Could't parse [" + source + "] using locale [" + getLocale() + "]";
            LOG.info(msg);
            throw new MovementParseException(msg);
        }
    }

    protected String[] split(final ImportChunk chunk){
        Assert.notNull(chunk, "ImportChunk cannot be null");
        Assert.notEmpty(chunk.getChunks(), "ImportChunk cannot be empty");
        Assert.isTrue(chunk.getChunks().size() == 1, "ImportChunk can only contain one line (for this bankCode)");
        return chunk.getChunks().get(0).split(getFieldsDelimiter());
    }

    protected abstract String getFieldsDelimiter();

    protected abstract String getDatePattern();

    protected abstract Locale getLocale();

    protected abstract String getAmountPattern();

    protected abstract BankAccountMovementData populateInternal(String[] source, BankAccountMovementData target);
}
