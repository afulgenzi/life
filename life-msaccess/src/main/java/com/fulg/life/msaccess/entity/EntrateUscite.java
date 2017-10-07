package com.fulg.life.msaccess.entity;

import java.util.Date;

public class EntrateUscite implements Comparable<EntrateUscite> {
	Integer IDEU;
	Integer IDEUMaster;
	Date Data;
	String Descrizione;
	Double Importo;
	Integer IDValutaImporto;
	String EU;
	Boolean Pagato;
	Boolean Preavviso;
	Byte GiorniPreavviso;
	Boolean Valido;
	String Tipologia;

	public Integer getIDEU() {
		return IDEU;
	}

	public void setIDEU(Integer iDEU) {
		IDEU = iDEU;
	}

	public Integer getIDEUMaster() {
		return IDEUMaster;
	}

	public void setIDEUMaster(Integer iDEUMaster) {
		IDEUMaster = iDEUMaster;
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

	public Double getImporto() {
		return Importo;
	}

	public void setImporto(Double importo) {
		Importo = importo;
	}

	public Integer getIDValutaImporto() {
		return IDValutaImporto;
	}

	public void setIDValutaImporto(Integer iDValutaImporto) {
		IDValutaImporto = iDValutaImporto;
	}

	public String getEU() {
		return EU;
	}

	public void setEU(String eU) {
		EU = eU;
	}

	public Boolean isPagato() {
		return Pagato;
	}

	public void setPagato(Boolean pagato) {
		Pagato = pagato;
	}

	public Boolean isPreavviso() {
		return Preavviso;
	}

	public void setPreavviso(Boolean preavviso) {
		Preavviso = preavviso;
	}

	public Byte getGiorniPreavviso() {
		return GiorniPreavviso;
	}

	public void setGiorniPreavviso(Byte giorniPreavviso) {
		GiorniPreavviso = giorniPreavviso;
	}

	public Boolean isValido() {
		return Valido;
	}

	public void setValido(Boolean valido) {
		Valido = valido;
	}

	public String getTipologia() {
		return Tipologia;
	}

	public void setTipologia(String tipologia) {
		Tipologia = tipologia;
	}

	public int compareTo(EntrateUscite eu) {
		if (this.Data == null) {
			return 0;
		} else {
			if (this.Data.before(eu.Data)) {
				return -1;
			} else if (this.Data.after(eu.Data)) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}
