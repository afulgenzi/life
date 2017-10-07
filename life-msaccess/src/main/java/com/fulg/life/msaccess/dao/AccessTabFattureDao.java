package com.fulg.life.msaccess.dao;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fulg.life.msaccess.entity.TabFatture;

public class AccessTabFattureDao extends BaseDao<TabFatture> {
    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(AccessTabFattureDao.class);

    public AccessTabFattureDao() {
        loadData(TabFatture.class);
    }

    @Override
    protected TabFatture createInstance() {
        return new TabFatture();
    }

    @Override
    public Integer getNextPK() {
        throw new NotImplementedException("");
    }
}
