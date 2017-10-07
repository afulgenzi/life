package com.fulg.life.facade.data;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by alessandro.fulgenzi on 12/05/16.
 */
public class ImportChunk {
    List<String> chunks = Lists.newArrayList();

    public List<String> getChunks() {
        return chunks;
    }

    public void setChunks(List<String> chunks) {
        this.chunks = chunks;
    }
}
