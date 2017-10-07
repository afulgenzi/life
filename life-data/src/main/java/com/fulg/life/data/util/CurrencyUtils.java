package com.fulg.life.data.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.fulg.life.data.CurrencyData;

public class CurrencyUtils {
	private static final String CURRENCY_DEFAULT_PATTERN = "0.00";

	public static String formatDouble(final double doubleValue, CurrencyData currency, String pattern) {
		NumberFormat formatter = new DecimalFormat(pattern);
		String output = formatter.format(doubleValue);
		return currency.getAbbreviation() + " " + output;
	}

	public static String formatDouble(final double doubleValue, CurrencyData currency) {
		return formatDouble(doubleValue, currency, CURRENCY_DEFAULT_PATTERN);
	}
}
