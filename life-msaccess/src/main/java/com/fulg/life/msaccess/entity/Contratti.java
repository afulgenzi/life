package com.fulg.life.msaccess.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Contratti {
    Integer CodiceCliente;
    Date DataInizio;
    Date DataFine;
    BigDecimal Tariffa;
    String NProtocollo;
    Date DataContratto;

    public Integer getCodiceCliente() {
        return CodiceCliente;
    }

    public void setCodiceCliente(Integer codiceCliente) {
        CodiceCliente = codiceCliente;
    }

    public Date getDataInizio() {
        return DataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        DataInizio = dataInizio;
    }

    public Date getDataFine() {
        return DataFine;
    }

    public void setDataFine(Date dataFine) {
        DataFine = dataFine;
    }

    public BigDecimal getTariffa() {
        return Tariffa;
    }

    public void setTariffa(BigDecimal tariffa) {
        Tariffa = tariffa;
    }

    public String getNProtocollo() {
        return NProtocollo;
    }

    public void setNProtocollo(String nProtocollo) {
        NProtocollo = nProtocollo;
    }

    public Date getDataContratto() {
        return DataContratto;
    }

    public void setDataContratto(Date dataContratto) {
        DataContratto = dataContratto;
    }
}
