package com.fulg.life.msaccess.dao;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fulg.life.msaccess.entity.TabClienti;

public class AccessTabClientiDao extends BaseDao<TabClienti> {
    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(AccessTabClientiDao.class);

    public AccessTabClientiDao() {
        loadData(TabClienti.class);
    }

    @Override
    protected TabClienti createInstance() {
        return new TabClienti();
    }

    @Override
    public Integer getNextPK() {
        throw new NotImplementedException("");
    }
}
