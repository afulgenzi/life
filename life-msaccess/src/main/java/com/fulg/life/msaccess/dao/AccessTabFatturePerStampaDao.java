package com.fulg.life.msaccess.dao;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fulg.life.msaccess.entity.TabFatturePerStampa;

public class AccessTabFatturePerStampaDao extends BaseDao<TabFatturePerStampa> {
    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(AccessTabFatturePerStampaDao.class);

    public AccessTabFatturePerStampaDao() {
        loadData(TabFatturePerStampa.class);
    }

    @Override
    protected TabFatturePerStampa createInstance() {
        return new TabFatturePerStampa();
    }

    @Override
    public Integer getNextPK() {
        throw new NotImplementedException("");
    }
}
