package com.fulg.life.msaccess.dao;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fulg.life.msaccess.entity.ScadenzeMaster;

public class AccessScadenzeMasterDao extends BaseDao<ScadenzeMaster> {
    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(AccessScadenzeMasterDao.class);

    public AccessScadenzeMasterDao() {
        loadData(ScadenzeMaster.class);
    }

    @Override
    protected ScadenzeMaster createInstance() {
        return new ScadenzeMaster();
    }

    @Override
    public Integer getNextPK() {
        throw new NotImplementedException("");
    }
}
