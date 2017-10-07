package com.fulg.life.facade.strategy;

import com.fulg.life.facade.data.ImportChunk;

import java.util.List;

/**
 * Created by alessandro.fulgenzi on 12/02/16.
 */
public interface BankMovementSplitterStrategy {
    List<ImportChunk> split(String text);
}
