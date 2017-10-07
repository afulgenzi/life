package com.fulg.life.facade.converter;

import com.fulg.life.data.BankAccountMovementData;
import com.fulg.life.facade.data.ImportChunk;
import com.fulg.life.model.entities.BankAccountMovement;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Locale;

public class IntesaImportMovementFromCsvConverter extends AbstractImportBankAccountMovementConverter {
    private static final Logger LOG = LoggerFactory.getLogger(IntesaImportMovementFromCsvConverter.class);

    private final static String DATE_PATTERN = "dd.MM.yyyy";
    private final static String AMOUNT_PATTERN = "###,##";

    @Override
    protected String getDatePattern() {
        return DATE_PATTERN;
    }

    @Override
    protected Locale getLocale() {
        return Locale.ITALY;
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
    protected String[] split(ImportChunk chunk) {
        Assert.notNull(chunk, "ImportChunk cannot be null");
        Assert.notEmpty(chunk.getChunks(), "ImportChunk cannot be empty");
        Assert.isTrue(chunk.getChunks().size() > 1, "ImportChunk must contain more than one line (for this bankCode)");

        completeChunk(chunk);

        final List<String> result = Lists.newArrayList();
        for (int ind = 0 ; ind < chunk.getChunks().size() ; ind++)
        {
            final String value = chunk.getChunks().get(ind);
            if (ind==0)
            {
                final String[] dates = value.split("\t");
                Assert.isTrue(dates.length == 2, "There must be two dates at position [" + ind + "]. Found [" + value + "]");
                result.add(dates[0]);
                result.add(dates[1]);
            }
            else
            {
                result.add(chunk.getChunks().get(ind));
            }
        }
        return result.toArray(new String[result.size()]);
    }

    protected void completeChunk(final ImportChunk chunk)
    {
        // Remove first blank lines
        while (StringUtils.isEmpty(chunk.getChunks().get(0)))
        {
            chunk.getChunks().remove(0);
        }
        // Remove last blank lines
        while (StringUtils.isEmpty(chunk.getChunks().get(chunk.getChunks().size()-1)))
        {
            chunk.getChunks().remove(chunk.getChunks().size()-1);
        }

        // If no empty amount found in latest two positions
        if (StringUtils.isNotBlank(chunk.getChunks().get(chunk.getChunks().size()-1)) &&
                StringUtils.isNotBlank(chunk.getChunks().get(chunk.getChunks().size()-2)))
        {
            // Add empty last line
            chunk.getChunks().add("");
        }
    }

    @Override
    public BankAccountMovementData populateInternal(String[] source, BankAccountMovementData target) {
        for (int ind = 0; ind < source.length; ind++)
        {
            LOG.debug("Field #{}: {}", ind, source[ind]);
        }

        // Date
        target.setDate(getDate(source[0]));

        // Description
        if (source.length==6)
        {
            target.setDescription(source[2] + " - " + source[3]);
        }
        else
        {
            target.setDescription(source[2]);
        }

        // Amount
        final double relativeAmount;
        if (StringUtils.isNotBlank(source[source.length-1]))
        {
            relativeAmount = getAmount(source[source.length-1]);
            target.setEu(BankAccountMovement.TYPE_IN);
        }
        else
        {
            relativeAmount = getAmount(source[source.length-2]);
            target.setEu(BankAccountMovement.TYPE_OUT);
        }
        target.setAmount(Math.abs(relativeAmount));

        return target;
    }

}
