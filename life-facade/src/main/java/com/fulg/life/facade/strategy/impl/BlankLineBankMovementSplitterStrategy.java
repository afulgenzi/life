package com.fulg.life.facade.strategy.impl;

import com.fulg.life.facade.data.ImportChunk;
import com.fulg.life.facade.strategy.BankMovementSplitterStrategy;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Created by alessandro.fulgenzi on 12/02/16.
 */
public class BlankLineBankMovementSplitterStrategy implements BankMovementSplitterStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(BlankLineBankMovementSplitterStrategy.class);

    @Override
    public List<ImportChunk> split(final String text) {
        final List<ImportChunk> result = Lists.newArrayList();

        // Split lines
        String[] lines = text.split("\n");
        ImportChunk chunk = new ImportChunk();
        for (String line : Arrays.asList(lines)) {
            if (line != null && line.trim().length() > 0) {
                // Add line to existing chunk
                chunk.getChunks().add(line);
            } else {
                // On blank line, copy chunk to result
                addChunkToResult(chunk, result);
                chunk = new ImportChunk();
            }
        }
        addChunkToResult(chunk, result);
        return result;
    }

    protected void addChunkToResult(final ImportChunk chunk, List<ImportChunk> result) {
        if (chunk != null && CollectionUtils.isNotEmpty(chunk.getChunks())) {
            result.add(chunk);
        }
    }
}
