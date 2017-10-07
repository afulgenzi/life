package com.fulg.life.data;

public class CurrencyData extends ItemData implements Comparable<CurrencyData> {
    String abbreviation;
    String code;
    String description;
    Double amountInLire;
    Double amountInEuro;
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getAmountInLire() {
		return amountInLire;
	}
	public void setAmountInLire(Double amountInLire) {
		this.amountInLire = amountInLire;
	}
	public Double getAmountInEuro() {
		return amountInEuro;
	}
	public void setAmountInEuro(Double amountInEuro) {
		this.amountInEuro = amountInEuro;
	}
    
	public int compareTo(CurrencyData o) {
		CurrencyData item = (CurrencyData) o;
		return item.getCode().compareTo(o.getCode());
	}
}
