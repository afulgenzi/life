package com.fulg.life.msaccess.entity;

import java.util.Date;

public class ScadenzeMaster {
    Integer IDScadenzaMaster;
    Integer PeriodicoOgni;
    Integer TipoPeriodicita;
    Date FinePeriodicita;

    public Integer getIDScadenzaMaster() {
        return IDScadenzaMaster;
    }

    public void setIDScadenzaMaster(Integer iDScadenzaMaster) {
        IDScadenzaMaster = iDScadenzaMaster;
    }

    public Integer getPeriodicoOgni() {
        return PeriodicoOgni;
    }

    public void setPeriodicoOgni(Integer periodicoOgni) {
        PeriodicoOgni = periodicoOgni;
    }

    public Integer getTipoPeriodicita() {
        return TipoPeriodicita;
    }

    public void setTipoPeriodicita(Integer tipoPeriodicita) {
        TipoPeriodicita = tipoPeriodicita;
    }

    public Date getFinePeriodicita() {
        return FinePeriodicita;
    }

    public void setFinePeriodicita(Date finePeriodicita) {
        FinePeriodicita = finePeriodicita;
    }
}
