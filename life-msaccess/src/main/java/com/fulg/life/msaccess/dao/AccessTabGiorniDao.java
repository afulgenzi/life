package com.fulg.life.msaccess.dao;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fulg.life.msaccess.entity.TabGiorni;

public class AccessTabGiorniDao extends BaseDao<TabGiorni> {
    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(AccessTabGiorniDao.class);

    public AccessTabGiorniDao() {
        loadData(TabGiorni.class);
    }

    @Override
    protected TabGiorni createInstance() {
        return new TabGiorni();
    }

    @Override
    public Integer getNextPK() {
        throw new NotImplementedException("");
    }
}
