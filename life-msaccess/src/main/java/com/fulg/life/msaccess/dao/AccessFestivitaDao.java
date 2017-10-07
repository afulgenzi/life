package com.fulg.life.msaccess.dao;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fulg.life.msaccess.entity.Festivita;

public class AccessFestivitaDao extends BaseDao<Festivita> {
    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(AccessFestivitaDao.class);

    public AccessFestivitaDao() {
        loadData(Festivita.class);
    }

    @Override
    protected Festivita createInstance() {
        return new Festivita();
    }

    @Override
    public Integer getNextPK() {
        throw new NotImplementedException("");
    }
}
