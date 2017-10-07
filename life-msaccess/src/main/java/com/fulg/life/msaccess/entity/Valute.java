package com.fulg.life.msaccess.entity;

public class Valute {
	Integer IDValuta;
	String Abbreviazione;
	String Codice;
	String Descrizione;
	Float ValoreInLire;
	Double ValoreInEuro;

	public Integer getIDValuta() {
		return IDValuta;
	}

	public void setIDValuta(Integer iDValuta) {
		IDValuta = iDValuta;
	}

	public String getAbbreviazione() {
		return Abbreviazione;
	}

	public void setAbbreviazione(String abbreviazione) {
		Abbreviazione = abbreviazione;
	}

	public String getCodice() {
		return Codice;
	}

	public void setCodice(String codice) {
		Codice = codice;
	}

	public String getDescrizione() {
		return Descrizione;
	}

	public void setDescrizione(String descrizione) {
		Descrizione = descrizione;
	}

	public Float getValoreInLire() {
		return ValoreInLire;
	}

	public void setValoreInLire(Float valoreInLire) {
		ValoreInLire = valoreInLire;
	}

	public Double getValoreInEuro() {
		return ValoreInEuro;
	}

	public void setValoreInEuro(Double valoreInEuro) {
		ValoreInEuro = valoreInEuro;
	}
}
