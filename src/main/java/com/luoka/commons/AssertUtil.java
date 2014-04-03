/**
 * Title:		luoka
 */
package com.luoka.commons;

import java.util.Collection;

/**
 * 常用断言方法. <br>
 * @since zengqingmeng @ Apr 3, 2014 8:49:03 PM
 *
 */
public class AssertUtil {
	/**
	 * 断言对象不为null.
	 * 
	 * @param obj
	 *            断言的对象
	 * @param message
	 *            提示信息
	 * @throws IllegalArgumentException
	 */
	public static void notNull(Object obj, String message) {
		if (obj == null) {
			throw new IllegalArgumentException(
					StringHelper.isEmpty(message) ? "the object is null!"
							: message);
		}
	}

	/**
	 * 断言字符串不为null，或者长度不为0（做trim）.
	 * 
	 * @param str
	 *            字符串
	 * @param message
	 *            提示信息
	 */
	public static void notNullOrEmpty(String str, String message) {
		if (str == null || str.trim().length() == 0) {
			throw new IllegalArgumentException(
					StringHelper.isEmpty(message) ? "the string is empty!"
							: message);
		}
	}

	/**
	 * 断言集合不为null，或者size不为0.
	 * 
	 * @param objs
	 *            集合
	 * @param message
	 *            提示信息
	 */
	public static void notNullOrEmpty(Collection<?> objs, String message) {
		notNull(objs, StringHelper.isEmpty(message) ? "the collection is null!"
				: message);
		if (objs.size() == 0) {
			throw new IllegalArgumentException(
					StringHelper.isEmpty(message) ? "the collection is empty!"
							: message);
		}
	}
}
