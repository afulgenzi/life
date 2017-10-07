package com.fulg.life.msaccess.entity;

import java.util.Date;

public class EUMaster {
    Integer IDEUMaster;
    Integer IntervalloPeriodicita;
    Integer TipoPeriodicita;
    Date InizioPeriodicita;
    Date FinePeriodicita;

    public Integer getIDEUMaster() {
        return IDEUMaster;
    }

    public void setIDEUMaster(Integer iDEUMaster) {
        IDEUMaster = iDEUMaster;
    }

    public Integer getIntervalloPeriodicita() {
        return IntervalloPeriodicita;
    }

    public void setIntervalloPeriodicita(Integer intervalloPeriodicita) {
        IntervalloPeriodicita = intervalloPeriodicita;
    }

    public Integer getTipoPeriodicita() {
        return TipoPeriodicita;
    }

    public void setTipoPeriodicita(Integer tipoPeriodicita) {
        TipoPeriodicita = tipoPeriodicita;
    }

    public Date getInizioPeriodicita() {
        return InizioPeriodicita;
    }

    public void setInizioPeriodicita(Date inizioPeriodicita) {
        InizioPeriodicita = inizioPeriodicita;
    }

    public Date getFinePeriodicita() {
        return FinePeriodicita;
    }

    public void setFinePeriodicita(Date finePeriodicita) {
        FinePeriodicita = finePeriodicita;
    }
}
