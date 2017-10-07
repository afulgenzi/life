package com.fulg.life.facade.strategy.impl;

import com.fulg.life.facade.data.ImportChunk;
import com.fulg.life.facade.strategy.BankMovementSplitterStrategy;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Created by alessandro.fulgenzi on 12/02/16.
 */
public class NewLineBankMovementSplitterStrategy implements BankMovementSplitterStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(NewLineBankMovementSplitterStrategy.class);

    @Override
    public List<ImportChunk> split(final String text) {
        List<ImportChunk> result = Lists.newArrayList();

        String[] lines = text.split("\n");
        for (String line : Arrays.asList(lines)) {
            if (line != null && line.trim().length() > 0) {
                final ImportChunk chunk = new ImportChunk();
                chunk.setChunks(Lists.<String>newArrayList(line));
                result.add(chunk);
            }
        }
        return result;
    }
}
