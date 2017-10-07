/*
 * TedBaker project.
 * Copyright (c) 2012-2016 Neoworks Limited
 * All rights reserved.
 */

package com.fulg.life.data.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by alessandro.fulgenzi on 27/07/16.
 */
public class BankHolidayUtils {
    private static final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    private static final boolean enableLogs = false;

    public static Integer getWorkDays(int year, int month)
    {
        int workDays = 0;
        int bankHolidays = 0;
        int totalDays = getDaysInMonth(year, month - 1);
        for (int day = 1; day <= totalDays; day++)
        {
            final Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month - 1);
            cal.set(Calendar.DAY_OF_MONTH, day);
            if (isWorkDay(cal))
            {
                workDays++;
            } else
            {
                bankHolidays++;
            }
        }
        if (enableLogs)
        {
            System.out.println(
                    "[" + year + "," + month + "], Total Days: " + totalDays + ", Working Days: " + workDays + ", Bank Holidays: " + bankHolidays);
        }
        return workDays;
    }

    private static int getDaysInMonth(int year, int month)
    {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    private static boolean isWorkDay(final Calendar cal)
    {
        if (isSaturdayOrSunday(cal))
        {
            return false;
        } else if (isBankHoliday(cal))
        {
            if (enableLogs)
            {
                System.out.println("Bank Holiday on [" + formatter.format(cal.getTime()) + "]");
            }
            return false;
        }
        return true;
    }

    private static boolean isSaturdayOrSunday(final Calendar cal)
    {
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return (Calendar.SATURDAY == dayOfWeek || Calendar.SUNDAY == dayOfWeek);
    }

    private static boolean isBankHoliday(final Calendar cal)
    {
        if (isNewYearsDayOrSubstitute(cal))
        {
            return true;
        } else if (isGoodFridayOrEasterMonday(cal))
        {
            return true;
        } else if (isEarlyMayBankHoliday(cal))
        {
            return true;
        } else if (isSpringBankHoliday(cal))
        {
            return true;
        } else if (isSummerBankHoliday(cal))
        {
            return true;
        } else if (isChristmasDayOrSubstitute(cal) || isBoxingDayOrSubstitute(cal))
        {
            return true;
        }
        return false;
    }

    private static boolean isNewYearsDayOrSubstitute(final Calendar cal)
    {
        int month = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (month == 0)
        {
            if (dayOfMonth == 1)
            {
                return true;
            } else if (dayOfMonth == 2 && Calendar.MONDAY == dayOfWeek)
            {
                return true;
            }
        }
        return false;
    }

    private static boolean isEarlyMayBankHoliday(final Calendar cal)
    {
        int month = cal.get(Calendar.MONTH);
        if (month == 4) // May
        {
            if (isFirstMonday(cal))
            {
                return true;
            }
        }
        return false;
    }

    private static boolean isSpringBankHoliday(final Calendar cal)
    {
        int month = cal.get(Calendar.MONTH);
        if (month == 4) // May
        {
            if (isLastMonday(cal))
            {
                return true;
            }
        }
        return false;
    }

    private static boolean isSummerBankHoliday(final Calendar cal)
    {
        int month = cal.get(Calendar.MONTH);
        if (month == 7) // August
        {
            if (isLastMonday(cal))
            {
                return true;
            }
        }
        return false;
    }

    private static boolean isChristmasDayOrSubstitute(final Calendar cal)
    {
        int month = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (month == 11) // December
        {
            if (dayOfMonth == 25)
            {
                return true;
            } else if (dayOfMonth == 26 && Calendar.MONDAY == dayOfWeek)
            {
                return true;
            }
        }
        return false;
    }

    private static boolean isBoxingDayOrSubstitute(final Calendar cal)
    {
        int month = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (month == 11) // December
        {
            if (dayOfMonth == 26)
            {
                return true;
            } else if (dayOfMonth == 27 && Calendar.MONDAY == dayOfWeek)
            {
                return true;
            } else if (dayOfMonth == 27 && Calendar.TUESDAY == dayOfWeek)
            {
                return true;
            }
        }
        return false;
    }

    private static boolean isGoodFridayOrEasterMonday(final Calendar cal)
    {
        int dayInYear = cal.get(Calendar.DAY_OF_YEAR);
        final Calendar easter = getEasterSunday(cal.get(Calendar.YEAR));
        int easterDayInYear = easter.get(Calendar.DAY_OF_YEAR);
        return (dayInYear == easterDayInYear - 2 || dayInYear == easterDayInYear + 1);
    }

    private static boolean isFirstMonday(final Calendar cal)
    {
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int dayOfWeekInMonth = cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);
        if (Calendar.MONDAY == dayOfWeek)
        {
            if (dayOfWeekInMonth == 1)
            {
                return true;
            }
        }
        return false;
    }

    private static boolean isLastMonday(final Calendar cal)
    {
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int dayOfWeekInMonth = cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);
        int daysInMonth = getDaysInMonth(year, month);
        if (Calendar.MONDAY == dayOfWeek)
        {
            if (dayOfWeekInMonth == 4 && daysInMonth - dayOfMonth < 7)
            {
                return true;
            } else if (dayOfWeekInMonth == 5)
            {
                return true;
            }
        }
        return false;
    }

    private static Calendar getEasterSunday(int year)
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

        int month = n - 1;

        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);

        return cal;
    }

    public static void main(String[] args)
    {
        final BankHolidayUtils test = new BankHolidayUtils();
        for (int year = 2016; year <= 2016; year++)
        {
            int yearlyWorkDays = 0;
            for (int month = 1; month <= 12; month++)
            {
                int monthlyWorkDays = test.getWorkDays(2016, month);
                yearlyWorkDays += monthlyWorkDays;
                System.out.println("[" + year + "," + month + "], Work Days: " + monthlyWorkDays);
            }
            System.out.println("[" + year + "], Work Days: " + yearlyWorkDays);
        }
    }
}
