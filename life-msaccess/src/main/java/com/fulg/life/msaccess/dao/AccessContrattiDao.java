package com.fulg.life.msaccess.dao;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fulg.life.msaccess.entity.Contratti;

public class AccessContrattiDao extends BaseDao<Contratti> {
    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(AccessContrattiDao.class);

    public AccessContrattiDao() {
        loadData(Contratti.class);
    }

    @Override
    protected Contratti createInstance() {
        return new Contratti();
    }

    @Override
    public Integer getNextPK() {
        throw new NotImplementedException("");
    }
}
