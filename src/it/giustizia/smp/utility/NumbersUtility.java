package it.giustizia.smp.utility;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.lang.StringUtils;

public final class NumbersUtility {

	public static BigInteger getBigInteger(String numero) {
		return StringUtils.isNumeric(numero) ? new BigInteger(numero) : null;
	}

	public static BigInteger getBigInteger(Short numero) {
		return BigInteger.valueOf((long) numero);
	}

	public static BigInteger getBigInteger(Long numero) {
		return (numero != null) ? BigInteger.valueOf(numero) : null;
	}

	public static BigInteger getBigInteger(Integer numero) {
		return (numero != null) ? BigInteger.valueOf(numero) : null;
	}

	public static BigInteger getBigInteger(BigDecimal numero) {
		return (numero != null) ? BigInteger.valueOf(numero.longValue()) : null;
	}

	public static long getLong(BigInteger numero) {
		return (numero != null) ? numero.longValue() : 0L;
	}

	public static int getInt(BigInteger numero) {
		return (numero != null) ? numero.intValue() : 0;
	}
}
