package com.fulg.life.migration.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class LegacyTable {
    private String name;
    private int size;
    private Date lastMigrated;
    private List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Date getLastMigrated() {
        return lastMigrated;
    }

    public void setLastMigrated(Date lastMigrated) {
        this.lastMigrated = lastMigrated;
    }

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }

}
