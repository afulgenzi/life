package com.fulg.life.facade.impl;

import com.fulg.life.data.DateData;
import com.fulg.life.data.MonthData;
import com.fulg.life.data.YearData;
import com.fulg.life.data.util.BankHolidayUtils;
import com.fulg.life.facade.DateTimeFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

public class DateTimeFacadeImpl implements DateTimeFacade {
    private static final String PROPERTY_MONTH_NAME_PREFIX = "month.name.";
    private static final String PROPERTY_MONTH_DISPLAY_NAME_PREFIX = "month.displayName.";

    @SuppressWarnings("unused")
    private static Logger LOG = LoggerFactory.getLogger(DateTimeFacadeImpl.class);

    @Resource(name = "lifeFacadeProperties")
    private Properties lifeProperties;

    private static final int START_YEAR = 2000;
    private static final int END_YEAR = 2050;

    @Override
    public List<MonthData> getMonths()
    {
        List<MonthData> monthList = new ArrayList<MonthData>();

        MonthData month = null;

        month = new MonthData();
        month.setCode(1);
        month.setName("january");
        month.setDisplayName("January");
        monthList.add(month);

        month = new MonthData();
        month.setCode(2);
        month.setName("february");
        month.setDisplayName("February");
        monthList.add(month);

        month = new MonthData();
        month.setCode(3);
        month.setName("march");
        month.setDisplayName("March");
        monthList.add(month);

        month = new MonthData();
        month.setCode(4);
        month.setName("april");
        month.setDisplayName("April");
        monthList.add(month);

        month = new MonthData();
        month.setCode(5);
        month.setName("may");
        month.setDisplayName("May");
        monthList.add(month);

        month = new MonthData();
        month.setCode(6);
        month.setName("june");
        month.setDisplayName("June");
        monthList.add(month);

        month = new MonthData();
        month.setCode(7);
        month.setName("july");
        month.setDisplayName("July");
        monthList.add(month);

        month = new MonthData();
        month.setCode(8);
        month.setName("august");
        month.setDisplayName("August");
        monthList.add(month);

        month = new MonthData();
        month.setCode(9);
        month.setName("september");
        month.setDisplayName("September");
        monthList.add(month);

        month = new MonthData();
        month.setCode(10);
        month.setName("october");
        month.setDisplayName("October");
        monthList.add(month);

        month = new MonthData();
        month.setCode(11);
        month.setName("november");
        month.setDisplayName("November");
        monthList.add(month);

        month = new MonthData();
        month.setCode(12);
        month.setName("december");
        month.setDisplayName("December");
        monthList.add(month);

        return monthList;
    }

    @Override
    public List<YearData> getYears()
    {
        List<YearData> result = new ArrayList<YearData>();

        YearData year = null;

        for (int ind = START_YEAR; ind <= END_YEAR; ind++)
        {
            year = new YearData();
            year.setCode(ind);
            year.setName("" + ind);
            year.setDisplayName("" + ind);
            result.add(year);
        }

        return result;
    }

    @Override
    public DateData getCurrentMonth()
    {
        GregorianCalendar now = new GregorianCalendar();
        int month = now.get(GregorianCalendar.MONTH);
        int year = now.get(GregorianCalendar.YEAR);

        return getMonth(year, month+1);
    }

    @Override
    public DateData getPreviousMonth(int year, int month)
    {
        if (month == 1)
        {
            return getMonth(year - 1, 12);
        } else
        {
            return getMonth(year, month - 1);
        }
    }

    @Override
    public DateData getNextMonth(int year, int month)
    {
        if (month == 12)
        {
            return getMonth(year + 1, 1);
        } else
        {
            return getMonth(year, month + 1);
        }
    }

    @Override
    public DateData getMonth(int year, int month)
    {
        DateData dateData = new DateData();
        dateData.setMonth(getMonthData(month));
        dateData.setYear(getYearData(year));
        dateData.setWorkDays(BankHolidayUtils.getWorkDays(year, month));
        return dateData;
    }

    public MonthData getMonthData(Integer month)
    {
        MonthData monthData = new MonthData();
        monthData.setCode(month);
        monthData.setName(lifeProperties.getProperty(PROPERTY_MONTH_NAME_PREFIX + month));
        monthData.setDisplayName(lifeProperties.getProperty(PROPERTY_MONTH_DISPLAY_NAME_PREFIX + month));
        return monthData;
    }

    public YearData getYearData(Integer year)
    {
        YearData yearData = new YearData();
        yearData.setCode(year);
        yearData.setName("" + year);
        yearData.setDisplayName("" + yearData);
        return yearData;
    }

}
