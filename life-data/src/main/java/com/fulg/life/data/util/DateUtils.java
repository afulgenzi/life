package com.fulg.life.data.util;

import com.fulg.life.data.FrequencyData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
    private static final Logger LOG = LoggerFactory.getLogger(DateUtils.class);

    private static final String DAYS = "d";
    private static final String WEEKS = "w";
    private static final String MONTHS = "m";
    private static final String YEARS = "y";

    public static int getYear(final Date date)
    {
        return get(date, Calendar.YEAR);
    }

    public static int getMonth(final Date date)
    {
        return get(date, Calendar.MONTH);
    }

    public static Date getDefaultStartDate()
    {
        final GregorianCalendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, 1900);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        return cal.getTime();
    }

    public static Date getDefaultEndDate()
    {
        final GregorianCalendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, 2099);
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DAY_OF_YEAR, 31);
        return cal.getTime();
    }

    private static int get(final Date date, final int field)
    {
        final GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(field);
    }

    public static Date calculateNextDate(final Date currentDate, final FrequencyData freq, final Date untilDate)
    {
        boolean untilDateSet = (untilDate != null);
        boolean freqIntervalSet = (freq != null && freq.getFrequencyInterval() > 0);
        boolean freqTypeSet = (freq != null && freq.getFrequencyType() != null);
        if (untilDateSet && freqIntervalSet && freqTypeSet)
        {
            final Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            if (DAYS.equals(freq.getFrequencyType().getIntervalUnitInternal()))
            {
                cal.add(Calendar.DAY_OF_YEAR, freq.getFrequencyInterval());
            } else if (WEEKS.equals(freq.getFrequencyType().getIntervalUnitInternal()))
            {
                cal.add(Calendar.WEEK_OF_YEAR, freq.getFrequencyInterval());
            } else if (MONTHS.equals(freq.getFrequencyType().getIntervalUnitInternal()))
            {
                cal.add(Calendar.MONTH, freq.getFrequencyInterval());
            } else if (YEARS.equals(freq.getFrequencyType().getIntervalUnitInternal()))
            {
                cal.add(Calendar.YEAR, freq.getFrequencyInterval());
            } else
            {
                throw new RuntimeException(
                        "Unrecognized Interval unit [" + freq.getFrequencyType().getIntervalUnitInternal() + "]");
            }
            if (cal.getTime().before(untilDate))
            {
                return cal.getTime();
            } else
            {
                return null;
            }
        } else
        {
            LOG.warn("Frequency not set properly. UntilDate [{}], Frequency Interval [{}], FrequencyType [{}]",
                    untilDateSet, freqIntervalSet, freqTypeSet);
            throw new RuntimeException(
                    "Frequency not set properly. UntilDate [" + untilDateSet + "], Frequency Interval [" + freqIntervalSet + "], FrequencyType [" + freqTypeSet + "]");
        }
    }

    public static String getEasterSundayDate(int year)
    {
        int a = year % 19,
                b = year / 100,
                c = year % 100,
                d = b / 4,
                e = b % 4,
                g = (8 * b + 13) / 25,
                h = (19 * a + b - d - g + 15) % 30,
                j = c / 4,
                k = c % 4,
                m = (a + 11 * h) / 319,
                r = (2 * e + 2 * j - k - h + m + 32) % 7,
                n = (h - m + r + 90) / 25,
                day = (h - m + r + n + 19) % 32;

        String result;
        switch (n)
        {
            case 3:
                result = "March ";
                break;
            case 4:
                result = "April ";
                break;
            default:
                result = "error";
        }

        int month = n - 1;

        return result + day;
    }


    /**
     * @param args
     */
    public static void main(String[] args)
    {
//        String myDate = "02-03-2011";
//
//        try
//        {
//            SimpleDateFormat formatter1 = new SimpleDateFormat("dd-mm-yyyy");
//            System.out.println(formatter1.parse(myDate));
//
//            SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy");
//            System.out.println(formatter2.parse(myDate));
//        } catch (ParseException e)
//        {
//            e.printStackTrace();
//        }
        for (int year = 1900; year < 2100; year++)
        {
            final String date = year + " " + DateUtils.getEasterSundayDate(year);
//            if (!date.contains("March") && !date.contains("April")){
                System.out.println(date);
//            }
        }
    }

}
