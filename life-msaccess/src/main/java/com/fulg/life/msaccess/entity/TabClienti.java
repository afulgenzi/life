package com.fulg.life.msaccess.entity;

import java.util.Date;

public class TabClienti {
    Integer CodiceCliente;
    String Denominazione;
    String Indirizzo;
    String CAP;
    String Provincia;
    String PartitaIVA;
    Date DataCancellazione;

    public Integer getCodiceCliente() {
        return CodiceCliente;
    }

    public void setCodiceCliente(Integer codiceCliente) {
        CodiceCliente = codiceCliente;
    }

    public String getDenominazione() {
        return Denominazione;
    }

    public void setDenominazione(String denominazione) {
        Denominazione = denominazione;
    }

    public String getIndirizzo() {
        return Indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        Indirizzo = indirizzo;
    }

    public String getCAP() {
        return CAP;
    }

    public void setCAP(String cAP) {
        CAP = cAP;
    }

    public String getProvincia() {
        return Provincia;
    }

    public void setProvincia(String provincia) {
        Provincia = provincia;
    }

    public String getPartitaIVA() {
        return PartitaIVA;
    }

    public void setPartitaIVA(String partitaIVA) {
        PartitaIVA = partitaIVA;
    }

    public Date getDataCancellazione() {
        return DataCancellazione;
    }

    public void setDataCancellazione(Date dataCancellazione) {
        DataCancellazione = dataCancellazione;
    }
}
