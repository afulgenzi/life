package com.fulg.life.msaccess.entity;

import java.util.Date;

public class Appuntamenti {
    Integer IDAppuntamento;
    Date Data;
    Date Ora;
    String Descrizione;
    Integer GiorniPreavviso;

    public Integer getIDAppuntamento() {
        return IDAppuntamento;
    }

    public void setIDAppuntamento(Integer iDAppuntamento) {
        IDAppuntamento = iDAppuntamento;
    }

    public Date getData() {
        return Data;
    }

    public void setData(Date data) {
        Data = data;
    }

    public Date getOra() {
        return Ora;
    }

    public void setOra(Date ora) {
        Ora = ora;
    }

    public String getDescrizione() {
        return Descrizione;
    }

    public void setDescrizione(String descrizione) {
        Descrizione = descrizione;
    }

    public Integer getGiorniPreavviso() {
        return GiorniPreavviso;
    }

    public void setGiorniPreavviso(Integer giorniPreavviso) {
        GiorniPreavviso = giorniPreavviso;
    }
}
