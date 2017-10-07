package com.fulg.life.msaccess.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fulg.life.msaccess.entity.Valute;

public class AccessValuteDao extends BaseDao<Valute> {
    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(AccessValuteDao.class);

    public AccessValuteDao() {
        loadData(Valute.class);
    }

    public Valute getByIdValute(long idValuta) {
        for (Valute Valute : entities) {
            if (Valute.getIDValuta() == idValuta) {
                return Valute;
            }
        }
        return null;
    }

    @Override
    protected Valute createInstance() {
        return new Valute();
    }

    @Override
    public Integer getNextPK() {
        int maxPk = 0;
        for (Valute entity : entities) {
            if (entity.getIDValuta() > maxPk) {
                maxPk = entity.getIDValuta();
            }
        }
        return Integer.valueOf(maxPk + 1);
    }
}
