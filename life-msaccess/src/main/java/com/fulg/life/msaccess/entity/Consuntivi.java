package com.fulg.life.msaccess.entity;

import java.util.Date;

public class Consuntivi {
    Integer Mese;
    Integer Anno;
    Integer OreLavorate;
    String Societa;
    String Cliente;
    String MeseInLettere;
    Date DataConsegna;

    public Integer getMese() {
        return Mese;
    }

    public void setMese(Integer mese) {
        Mese = mese;
    }

    public Integer getAnno() {
        return Anno;
    }

    public void setAnno(Integer anno) {
        Anno = anno;
    }

    public Integer getOreLavorate() {
        return OreLavorate;
    }

    public void setOreLavorate(Integer oreLavorate) {
        OreLavorate = oreLavorate;
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

    public String getMeseInLettere() {
        return MeseInLettere;
    }

    public void setMeseInLettere(String meseInLettere) {
        MeseInLettere = meseInLettere;
    }

    public Date getDataConsegna() {
        return DataConsegna;
    }

    public void setDataConsegna(Date dataConsegna) {
        DataConsegna = dataConsegna;
    }
}
