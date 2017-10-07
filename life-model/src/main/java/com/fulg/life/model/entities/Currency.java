package com.fulg.life.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "currencies")
public class Currency extends Item {

    @Column(name = "ABBREVIATION")
	String abbreviation;
	@Column(name = "CODE")
	String code;
	@Column(name = "DESCRIPTION")
	String description;
	@Column(name = "amountInLire")
	Double amountInLire;
	@Column(name = "amountInEuro")
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
}
