package com.fulg.life.msaccess.entity;

import java.util.Date;

public class TabGiorni {
    Integer IdPeriodo;
    Integer IdGiorno;
    String Progetto;
    Date OraEntrata;
    Date OraUscita;
    Float OreOrdinarie;
    Float OreStraordinarie;
    Float Ferie;
    String Note;

    public Integer getIdPeriodo() {
        return IdPeriodo;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        IdPeriodo = idPeriodo;
    }

    public Integer getIdGiorno() {
        return IdGiorno;
    }

    public void setIdGiorno(Integer idGiorno) {
        IdGiorno = idGiorno;
    }

    public String getProgetto() {
        return Progetto;
    }

    public void setProgetto(String progetto) {
        Progetto = progetto;
    }

    public Date getOraEntrata() {
        return OraEntrata;
    }

    public void setOraEntrata(Date oraEntrata) {
        OraEntrata = oraEntrata;
    }

    public Date getOraUscita() {
        return OraUscita;
    }

    public void setOraUscita(Date oraUscita) {
        OraUscita = oraUscita;
    }

    public Float getOreOrdinarie() {
        return OreOrdinarie;
    }

    public void setOreOrdinarie(Float oreOrdinarie) {
        OreOrdinarie = oreOrdinarie;
    }

    public Float getOreStraordinarie() {
        return OreStraordinarie;
    }

    public void setOreStraordinarie(Float oreStraordinarie) {
        OreStraordinarie = oreStraordinarie;
    }

    public Float getFerie() {
        return Ferie;
    }

    public void setFerie(Float ferie) {
        Ferie = ferie;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }
}
