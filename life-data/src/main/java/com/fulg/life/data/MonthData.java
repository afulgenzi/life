package com.fulg.life.data;

public class MonthData extends ItemData {
	private static final long serialVersionUID = -2195186119947252080L;
	
	Integer code;
	String name;
	String displayName;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
