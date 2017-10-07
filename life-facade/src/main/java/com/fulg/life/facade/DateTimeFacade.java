package com.fulg.life.facade;

import com.fulg.life.data.DateData;
import com.fulg.life.data.MonthData;
import com.fulg.life.data.YearData;

import java.util.List;

public interface DateTimeFacade {
	List<MonthData> getMonths();

	List<YearData> getYears();

	MonthData getMonthData(Integer month);

	YearData getYearData(Integer year);

	DateData getCurrentMonth();

	DateData getPreviousMonth(int year, int month);

	DateData getNextMonth(int year, int month);

	DateData getMonth(int year, int month);
}
