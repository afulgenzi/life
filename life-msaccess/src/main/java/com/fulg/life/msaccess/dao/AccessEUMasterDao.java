package com.fulg.life.msaccess.dao;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fulg.life.msaccess.entity.EUMaster;

public class AccessEUMasterDao extends BaseDao<EUMaster> {
    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(AccessEUMasterDao.class);

    public AccessEUMasterDao() {
        loadData(EUMaster.class);
    }

    @Override
    protected EUMaster createInstance() {
        return new EUMaster();
    }

    @Override
    public Integer getNextPK() {
        throw new NotImplementedException("");
    }
}
