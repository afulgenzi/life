package com.fulg.life.msaccess.entity;

import java.util.Date;

public class Scadenze {
    Integer IDScadenza;
    Integer IDScadenzaMaster;
    Date Data;
    String Descrizione;
    Integer Importo;
    Integer IDValuta;
    Boolean Pagato;
    Boolean Avviso;
    Integer GiorniPreavviso;

    public Integer getIDScadenza() {
        return IDScadenza;
    }

    public void setIDScadenza(Integer iDScadenza) {
        IDScadenza = iDScadenza;
    }

    public Integer getIDScadenzaMaster() {
        return IDScadenzaMaster;
    }

    public void setIDScadenzaMaster(Integer iDScadenzaMaster) {
        IDScadenzaMaster = iDScadenzaMaster;
    }

    public Date getData() {
        return Data;
    }

    public void setData(Date data) {
        Data = data;
    }

    public String getDescrizione() {
        return Descrizione;
    }

    public void setDescrizione(String descrizione) {
        Descrizione = descrizione;
    }

    public Integer getImporto() {
        return Importo;
    }

    public void setImporto(Integer importo) {
        Importo = importo;
    }

    public Integer getIDValuta() {
        return IDValuta;
    }

    public void setIDValuta(Integer iDValuta) {
        IDValuta = iDValuta;
    }

    public Boolean isPagato() {
        return Pagato;
    }

    public void setPagato(Boolean pagato) {
        Pagato = pagato;
    }

    public Boolean isAvviso() {
        return Avviso;
    }

    public void setAvviso(Boolean avviso) {
        Avviso = avviso;
    }

    public Integer getGiorniPreavviso() {
        return GiorniPreavviso;
    }

    public void setGiorniPreavviso(Integer giorniPreavviso) {
        GiorniPreavviso = giorniPreavviso;
    }
}
