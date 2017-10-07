package com.fulg.life.msaccess.dao;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fulg.life.msaccess.entity.Consuntivi;

public class AccessConsuntiviDao extends BaseDao<Consuntivi> {
    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(AccessConsuntiviDao.class);

    public AccessConsuntiviDao() {
        loadData(Consuntivi.class);
    }

    @Override
    protected Consuntivi createInstance() {
        return new Consuntivi();
    }

    @Override
    public Integer getNextPK() {
        throw new NotImplementedException("");
    }
}
