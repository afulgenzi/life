package com.fulg.life.facade.strategy.impl;

import com.fulg.life.data.BankAccountMovementData;
import com.fulg.life.facade.converter.AbstractImportBankAccountMovementConverter;
import com.fulg.life.facade.converter.MovementParseException;
import com.fulg.life.facade.data.ImportChunk;
import com.fulg.life.facade.strategy.BankAccountMovementParseStrategy;
import com.fulg.life.facade.strategy.BankMovementSplitterStrategy;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;
import java.util.Map;

/**
 * Created by alessandro.fulgenzi on 12/02/16.
 */
public class DefaultBankAccountMovementParseStrategy implements BankAccountMovementParseStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultBankAccountMovementParseStrategy.class);

    private Map<String, AbstractImportBankAccountMovementConverter> converters;
    private Map<String, BankMovementSplitterStrategy> splitters;

    @Override
    public List<BankAccountMovementData> parseText(final String bankCode, String text) {
        final List<BankAccountMovementData> result = Lists.newArrayList();
        final List<ImportChunk> chunks = getSplitters().get(bankCode).split(text);

    	AbstractImportBankAccountMovementConverter converter = getConverters().get(bankCode);
        if (converter != null){
            for (ImportChunk chunk : chunks) {
                if (chunks != null && chunks.size() > 0) {
                        try
                        {
                            final BankAccountMovementData movementData = converter.convert(chunk);
                            result.add(movementData);
                        }catch(MovementParseException ex)
                        {
                            throw new ConvertException("Can't convert line [" + chunk.getChunks().toString() + "] for bankCode [" + bankCode + "] using converter [" + converter.getClass().getSimpleName() + "]", ex);
                        }
                }
            }
        }
        else
        {
            LOG.warn("Can't find converter for bank account code [{}]", bankCode);
        }
        return result;
    }

    protected Map<String, AbstractImportBankAccountMovementConverter> getConverters() {
        return converters;
    }

    @Required
    public void setConverters(Map<String, AbstractImportBankAccountMovementConverter> converters) {
        this.converters = converters;
    }

    public Map<String, BankMovementSplitterStrategy> getSplitters() {
        return splitters;
    }

    public void setSplitters(Map<String, BankMovementSplitterStrategy> splitters) {
        this.splitters = splitters;
    }


}
