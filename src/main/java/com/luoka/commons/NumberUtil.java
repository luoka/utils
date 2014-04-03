/**
 * Title:		luoka
 */
package com.luoka.commons;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 处理数字相关的工具类.<br>
 * @since zengqingmeng @ Apr 3, 2014 9:28:16 PM
 *
 */
public class NumberUtil {
	private final static int STORE_UNITS = 1024;
	private final static String K_NUITS = "K";
	private final static String M_NUITS = "M";
	private final static String G_NUITS = "G";
	private final static String BYTE_NUITS = "BYTE";

	/**
	 * 将String转换为int，若转换失败则默认为0
	 * 
	 * @param str
	 * @return
	 */
	public static int parseInt(String str) {
		return parseInt(str, 0);
	}

	/**
	 * 将String转换为整数，若转换失败则返回一个默认值; 有小数点时会尝试转为小数再取整。 如果不需要支持小数，请使用
	 * {@link StringHelper#parseInt(String)} 。
	 * 
	 * @param str
	 *            要转换的字符串
	 * @param defaultValue
	 * @return
	 */
	public static int parseInt(String str, int defaultValue) {
		if (str == null) {
			return defaultValue;
		}
		str = str.trim();
		if (str.indexOf('.') > 0) {
			try {
				return (int) Float.parseFloat(str);
			} catch (Exception e) {
				return defaultValue;
			}
		}
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * 将对象转换为int，若转换失败则返回预定的默认值.
	 * 
	 * @param obj
	 * @param defVal
	 * @return
	 */
	public static int parseInt(Object obj, int defVal) {
		if (obj == null) {
			return defVal;
		}
		if (obj instanceof String) {
			return parseInt((String) obj, defVal);
		}
		if (obj instanceof Number) {
			return ((Number) obj).intValue();
		}
		return defVal;
	}

	/**
	 * 将String转换为int，若转换失败则返回0.
	 * 
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static long parseLong(String str) {
		return parseLong(str, 0);
	}

	/**
	 * 将String转换为int，若转换失败则返回一个默认值.
	 * 
	 * @param str
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public static long parseLong(String str, long defaultValue) {
		if (str == null) {
			return defaultValue;
		}
		str = str.trim();
		try {
			return Long.parseLong(str);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * 将long型转化为double型
	 * 
	 * @param value
	 * @return
	 */
	public static double transLongToDouble(long value) {
		return new Long(value).doubleValue();
	}

	/**
	 * 将String型转化为double型
	 * 
	 * @param value
	 * @return
	 */
	public static double parseDouble(String value) {
		long valueAsLong = parseLong(value);
		return transLongToDouble(valueAsLong);
	}

	/**
	 * 将double值转化为制定类型的字符串
	 * 
	 * @param doubleValue
	 * @param pattern
	 * @return
	 */
	public static String getFormatFromDouble(double doubleValue, String pattern) {
		NumberFormat nf = new DecimalFormat(pattern.trim());
		// 对0的判断
		if (0.0 == Math.abs(doubleValue))
			return 0 + nf.format(doubleValue);

		// 对正无穷小的判断
		if (doubleValue > 0 && 0.0 == Math.floor(doubleValue))
			return 0 + nf.format(doubleValue);

		// 对负无穷大的判断
		if (doubleValue < 0 && 0.0 == Math.ceil(doubleValue))
			return "-0" + nf.format(Math.abs(doubleValue));

		return nf.format(doubleValue);
	}

	/**
	 * 按照要求转换数据，如果units、pattern为空，则返回默认值原值+byte
	 * 
	 * @param count
	 * @param units
	 * @param pattern
	 * @return
	 */
	public static String getSpaceAsUnits(long count, String units,
			String pattern) {
		if (StringHelper.isEmpty(units)
				|| StringHelper.isEmpty(pattern))
			return count + BYTE_NUITS;

		units = units.trim();
		pattern = pattern.trim();

		if (K_NUITS.equals(units))
			return getFormatFromDouble(transLongToDouble(count) / STORE_UNITS,
					pattern)
					+ K_NUITS;
		else if (M_NUITS.equals(units))
			return getFormatFromDouble(transLongToDouble(count)
					/ Math.pow(STORE_UNITS, 2), pattern)
					+ M_NUITS;
		else if (G_NUITS.equals(units))
			return getFormatFromDouble(transLongToDouble(count)
					/ Math.pow(STORE_UNITS, 3), pattern)
					+ G_NUITS;
		else
			return count + BYTE_NUITS;
	}

	/**
	 * 获取百分比,只处理正数
	 * 
	 * @param dividend
	 *            被除数
	 * @param divisor
	 *            除数
	 * @return
	 */
	public static double getPercentage(double dividend, double divisor) {
		if(0 == divisor)
			throw new IllegalArgumentException("divisor is 0!");
		return dividend / divisor * 100;
	}

	/**
	 * 
	 * @param dividend
	 * @param divisor
	 * @return
	 * @see com.trs.dev4.jdk16.JDKTest#testDouble()
	 */
	public static int getPercentageAsInt(long dividend, long divisor) {
		if (divisor == 0) {
			return 0;
		}
		return (int) (((double) dividend / (double) divisor) * 100);
	}

	/**
	 * 返回两个数中较小的。
	 * 
	 * @param a
	 *            第一个数
	 * @param b
	 *            第二个数
	 * @return 两个数中较小的。
	 */
	public static int getMin(int a, int b) {
		return (a > b) ? b : a;
	}

	/**
	 * 返回两个数中较大的。
	 * 
	 * @param a
	 *            第一个数
	 * @param b
	 *            第二个数
	 * @return 两个数中较大的。
	 */
	public static int getMax(int a, int b) {
		return (a < b) ? b : a;
	}

	/**
	 * 从0到duration范围, 取total个平均数.
	 * 
	 * @param duration
	 *            给定的范围
	 * @param total
	 *            取多少个
	 * @return 得到的一系列数构成的数组
	 */
	public static double[] sequential(final double duration, final int total) {
		double[] result = new double[total];
		for (int i = 1; i <= result.length; i++) {
			result[i - 1] = duration * i / total;
		}
		return result;
	}

	/**
	 * 四舍五入保留小数.
	 * 
	 * @param origin
	 *            原数
	 * @param bitsAfterDot
	 *            保留几位小数; 如小于等于0则会自动调整为按保留两位处理
	 */
	public static double roundHalfUp(double origin, int bitsAfterDot) {
		BigDecimal b = new BigDecimal(origin);
		return b.setScale(bitsAfterDot, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 将int型两数相除，四舍五入保留小数.
	 * 
	 * @param dividend
	 *            除数
	 * @param divisor
	 *            被除数
	 * @param bitsAfterDot
	 *            保留几位小数; 如小于等于0则会自动调整为按保留两位处理
	 * @return
	 */
	public static double dividedAndHalfUp(int dividend, int divisor,
			int bitsAfterDot) {

		return roundHalfUp((double) dividend / divisor, bitsAfterDot);
	}
}
