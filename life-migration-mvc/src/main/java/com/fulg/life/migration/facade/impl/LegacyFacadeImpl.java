package com.fulg.life.migration.facade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.fulg.life.migration.dto.LegacyTable;
import com.fulg.life.migration.facade.LegacyFacade;
import com.fulg.life.msaccess.dao.BaseDao;

public class LegacyFacadeImpl implements LegacyFacade, ApplicationContextAware {
    private static final String DAO_NAME_PREFIX = "access";
    private static final String DAO_NAME_SUFFIX = "Dao";

    ApplicationContext applicationContext;

    @Override
    public LegacyTable getTableInfo(String tableName, boolean loadContent) {
        String daoName = DAO_NAME_PREFIX + tableName + DAO_NAME_SUFFIX;
        BaseDao dao = (BaseDao) applicationContext.getBean(daoName);
        List<Map<String, Object>> rows = dao.getAllAsRawData();

        LegacyTable legacyTable = new LegacyTable();
        legacyTable.setLastMigrated(null);
        legacyTable.setName(tableName);
        legacyTable.setSize(rows.size());

        if (loadContent) {
            legacyTable.setRows(rows);
        }

        return legacyTable;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
