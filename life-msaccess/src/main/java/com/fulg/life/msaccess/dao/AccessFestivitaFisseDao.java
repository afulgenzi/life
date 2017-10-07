package com.fulg.life.msaccess.dao;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fulg.life.msaccess.entity.FestivitaFisse;

public class AccessFestivitaFisseDao extends BaseDao<FestivitaFisse> {
    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(AccessFestivitaFisseDao.class);

    public AccessFestivitaFisseDao() {
        loadData(FestivitaFisse.class);
    }

    @Override
    protected FestivitaFisse createInstance() {
        return new FestivitaFisse();
    }

    @Override
    public Integer getNextPK() {
        throw new NotImplementedException("");
    }
}
