package com.fulg.life.msaccess.entity;

import java.util.Date;

public class TabPeriodi {
    Integer IdPeriodo;
    String Societa;
    String Cliente;
    String Mese;
    Integer Anno;
    Float TotPeriodo;
    Date DataConsegna;

    public Integer getIdPeriodo() {
        return IdPeriodo;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        IdPeriodo = idPeriodo;
    }

    public String getSocieta() {
        return Societa;
    }

    public void setSocieta(String societa) {
        Societa = societa;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getMese() {
        return Mese;
    }

    public void setMese(String mese) {
        Mese = mese;
    }

    public Integer getAnno() {
        return Anno;
    }

    public void setAnno(Integer anno) {
        Anno = anno;
    }

    public Float getTotPeriodo() {
        return TotPeriodo;
    }

    public void setTotPeriodo(Float totPeriodo) {
        TotPeriodo = totPeriodo;
    }

    public Date getDataConsegna() {
        return DataConsegna;
    }

    public void setDataConsegna(Date dataConsegna) {
        DataConsegna = dataConsegna;
    }
}
