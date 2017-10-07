package com.fulg.life.data.util;

import java.math.BigDecimal;

/**
 * Classe di utilita' per la manipolazione di BigDecimal
 * 
 * @author Alessandro
 * 
 */
public class BigDecimalUtils {
	public static BigDecimal fromDoubleToBigDecimal(final Double doubleObj,
			final int decimalDigits) {
		return fromDoubleToBigDecimal(doubleObj.doubleValue(), decimalDigits);
	}

	/**
	 * Converte da "double" a BigDecimal mantenendo un numero arbitrario di
	 * decimali
	 * 
	 * @param doubleValue
	 * @param decimalDigits
	 * @return BigDecimal
	 */
	public static BigDecimal fromDoubleToBigDecimal(final double doubleValue,
			final int decimalDigits) {
		final int intValue = Double.valueOf(
				doubleValue * Math.pow(10.0, decimalDigits)).intValue();
		return new BigDecimal(intValue).movePointLeft(decimalDigits);
	}
}
