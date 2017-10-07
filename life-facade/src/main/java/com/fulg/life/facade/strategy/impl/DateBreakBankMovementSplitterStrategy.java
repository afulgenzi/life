package com.fulg.life.facade.strategy.impl;

import com.fulg.life.facade.data.ImportChunk;
import com.fulg.life.facade.strategy.BankMovementSplitterStrategy;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by alessandro.fulgenzi on 12/02/16.
 */
public class DateBreakBankMovementSplitterStrategy implements BankMovementSplitterStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(DateBreakBankMovementSplitterStrategy.class);

    @Override
    public List<ImportChunk> split(final String text) {
        final List<ImportChunk> result = Lists.newArrayList();

        // Split lines
        String[] lines = text.split("\n");
        ImportChunk chunk = new ImportChunk();
        for (String line : Arrays.asList(lines)) {
            if (containsDate(line)) {
                // On line containing a Date, copy chunk to result
                addChunkToResult(chunk, result);
                chunk = new ImportChunk();
            }
            // Add line to existing chunk
            LOG.debug("Adding: {}", line);
            chunk.getChunks().add(line);
        }
        addChunkToResult(chunk, result);
        return result;
    }

    protected boolean containsDate(final String line) {
        // String to be scanned to find the pattern.
        String pattern = "(\\d{2})(\\.+)(\\d{2})(\\.+)(\\d{4})(.*)";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(line);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }

    protected void addChunkToResult(final ImportChunk chunk, List<ImportChunk> result) {
        LOG.debug("-------------------------");
        LOG.debug("Adding chunk to result, Lines [{}]", chunk.getChunks().size());
        LOG.debug("-------------------------");
        if (chunk != null && CollectionUtils.isNotEmpty(chunk.getChunks())) {
            result.add(chunk);
        }
    }
}
