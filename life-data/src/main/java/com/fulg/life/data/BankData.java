package com.fulg.life.data;

public class BankData extends ItemData implements Comparable<BankData> {
	String code;
	String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int compareTo(BankData o) {
		BankData item = (BankData) o;
		return item.getCode().compareTo(o.getCode());
	}

}
