package com.fulg.life.msaccess.dao;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fulg.life.msaccess.entity.TabPeriodi;

public class AccessTabPeriodiDao extends BaseDao<TabPeriodi> {
    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(AccessTabPeriodiDao.class);

    public AccessTabPeriodiDao() {
        loadData(TabPeriodi.class);
    }

    @Override
    protected TabPeriodi createInstance() {
        return new TabPeriodi();
    }

    @Override
    public Integer getNextPK() {
        throw new NotImplementedException("");
    }
}
