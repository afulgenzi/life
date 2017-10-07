package com.fulg.life.msaccess.dao;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fulg.life.msaccess.entity.Appuntamenti;

public class AccessAppuntamentiDao extends BaseDao<Appuntamenti> {
    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(AccessAppuntamentiDao.class);

    public AccessAppuntamentiDao() {
        loadData(Appuntamenti.class);
    }

    @Override
    protected Appuntamenti createInstance() {
        return new Appuntamenti();
    }

    @Override
    public Integer getNextPK() {
        throw new NotImplementedException("");
    }
}
