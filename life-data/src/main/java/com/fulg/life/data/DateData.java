package com.fulg.life.data;

public class DateData extends ItemData {
	MonthData month;
	YearData year;
	Integer workDays;

	public MonthData getMonth() {
		return month;
	}

	public void setMonth(MonthData month) {
		this.month = month;
	}

	public YearData getYear() {
		return year;
	}

	public void setYear(YearData year) {
		this.year = year;
	}

	public Integer getWorkDays()
	{
		return workDays;
	}

	public void setWorkDays(Integer workDays)
	{
		this.workDays = workDays;
	}
}
