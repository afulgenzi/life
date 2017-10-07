package com.fulg.life.msaccess.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TabFatturePerStampa {
    Integer NFattura;
    Date DataEmissione;
    Integer CodiceCliente;
    BigDecimal TariffaGiornaliera;
    Double GiorniLavorati;
    String Descrizione;
    Integer INPS;
    Integer IVA;
    Integer Ritenuta;
    BigDecimal Totale;
    BigDecimal Totale1;

    public Integer getNFattura() {
        return NFattura;
    }

    public void setNFattura(Integer nFattura) {
        NFattura = nFattura;
    }

    public Date getDataEmissione() {
        return DataEmissione;
    }

    public void setDataEmissione(Date dataEmissione) {
        DataEmissione = dataEmissione;
    }

    public Integer getCodiceCliente() {
        return CodiceCliente;
    }

    public void setCodiceCliente(Integer codiceCliente) {
        CodiceCliente = codiceCliente;
    }

    public BigDecimal getTariffaGiornaliera() {
        return TariffaGiornaliera;
    }

    public void setTariffaGiornaliera(BigDecimal tariffaGiornaliera) {
        TariffaGiornaliera = tariffaGiornaliera;
    }

    public Double getGiorniLavorati() {
        return GiorniLavorati;
    }

    public void setGiorniLavorati(Double giorniLavorati) {
        GiorniLavorati = giorniLavorati;
    }

    public String getDescrizione() {
        return Descrizione;
    }

    public void setDescrizione(String descrizione) {
        Descrizione = descrizione;
    }

    public Integer getINPS() {
        return INPS;
    }

    public void setINPS(Integer iNPS) {
        INPS = iNPS;
    }

    public Integer getIVA() {
        return IVA;
    }

    public void setIVA(Integer iVA) {
        IVA = iVA;
    }

    public Integer getRitenuta() {
        return Ritenuta;
    }

    public void setRitenuta(Integer ritenuta) {
        Ritenuta = ritenuta;
    }

    public BigDecimal getTotale() {
        return Totale;
    }

    public void setTotale(BigDecimal totale) {
        Totale = totale;
    }

    public BigDecimal getTotale1() {
        return Totale1;
    }

    public void setTotale1(BigDecimal totale1) {
        Totale1 = totale1;
    }
}
