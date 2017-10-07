package com.fulg.life.msaccess.dao;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fulg.life.msaccess.entity.Scadenze;

public class AccessScadenzeDao extends BaseDao<Scadenze> {
    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(AccessScadenzeDao.class);

    public AccessScadenzeDao() {
        loadData(Scadenze.class);
    }

    @Override
    protected Scadenze createInstance() {
        return new Scadenze();
    }

    @Override
    public Integer getNextPK() {
        throw new NotImplementedException("");
    }
}
