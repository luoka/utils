/**
 * Title:		luoka
 */
package com.luoka.commons;

/**
 * 显示时间间隔.
 * @since zengqingmeng @ Apr 3, 2014 10:10:34 PM
 *
 */
public class Duration {
	/**
	 * 构造时对传入的数值以秒作为单位.
	 */
	// TODO: 改为枚举
	public static final int UNIT_AS_MS = 1;

	/**
	 * 构造时对传入的数值以豪秒作为单位.
	 */
	public static final int UNIT_AS_SECOND = 10;

	/**
	 * 毫秒数.
	 */
	private int msEstimated;

	/**
	 * 秒数.
	 */
	private int secEstimated;

	public Duration(int estimated) {
		init(estimated, UNIT_AS_SECOND);
	}

	/**
	 * 用给定的字符串形式的秒数, 构造时长对象.
	 * 
	 * @throws IllegalArgumentException
	 *             参数<code>sEstimated</code>为null或empty.
	 * @throws NumberFormatException
	 *             if <code>sEstimated</code> does not contain a parsable
	 *             integer.
	 * @see #init(int, int)
	 */
	public Duration(String sEstimated) {
		if (sEstimated == null || sEstimated.trim().length() == 0) {
			throw new IllegalArgumentException("the string: [" + sEstimated + "] cannot convert to a integer!");
		}
		int estimated = Integer.parseInt(sEstimated);
		init(estimated, UNIT_AS_SECOND);
	}

	private void init(int estimated, int unit) {
		if (estimated < 0) {
			throw new IllegalArgumentException("the integer: " + estimated + " not a positive number!");
		}

		switch (unit) {
		case UNIT_AS_MS:
			this.msEstimated = estimated;
			this.secEstimated = estimated / 1000;
			break;

		case UNIT_AS_SECOND:
			this.msEstimated = estimated * 1000;
			this.secEstimated = estimated;
			break;

		default:
			throw new IllegalArgumentException("unsupport unit: " + unit);
		}
	}

	/**
	 * 以"HH:mm:SS"格式显示, 如果不存在小时部分, 则仅显示"mm:SS"形式.
	 * 
	 * @see DurationTest#testDefaultShow()
	 */
	public String defaultShow() {
		int nHour = secEstimated / 3600;
		int nMin = (secEstimated - (nHour * 3600)) / 60;
		int nSec = secEstimated - (nHour * 3600) - (nMin * 60);
		StringBuffer sb = new StringBuffer();
		if (nHour > 0) {
			sb.append((nHour > 9) ? String.valueOf(nHour) : "0" + nHour).append(":");
		}

		sb.append((nMin > 9) ? String.valueOf(nMin) : "0" + nMin).append(":");
		sb.append((nSec > 9) ? String.valueOf(nSec) : "0" + nSec);
		return sb.toString();
	}

	/**
	 * 总是以"HH:mm:SS"格式显示.
	 */
	public String hmsShow() {
		int nHour = secEstimated / 3600;
		int nMin = (secEstimated - (nHour * 3600)) / 60;
		int nSec = secEstimated - (nHour * 3600) - (nMin * 60);

		StringBuffer sb = new StringBuffer();
		sb.append((nHour > 9) ? String.valueOf(nHour) : "0" + nHour).append(":");
		sb.append((nMin > 9) ? String.valueOf(nMin) : "0" + nMin).append(":");
		sb.append((nSec > 9) ? String.valueOf(nSec) : "0" + nSec);
		return sb.toString();
	}

	/**
	 * Get the {@link #msEstimated}.
	 * 
	 * @return the {@link #msEstimated}.
	 */
	public int getMsEstimated() {
		return msEstimated;
	}

	/**
	 * Get the {@link #secEstimated}.
	 * 
	 * @return the {@link #secEstimated}.
	 */
	public int getSecEstimated() {
		return secEstimated;
	}
}
