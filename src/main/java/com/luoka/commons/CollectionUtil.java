/**
 * Title:		luoka
 */
package com.luoka.commons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * 对JCF中定义的数据结构接口的工具类.<br>
 * @since zengqingmeng @ Apr 3, 2014 9:25:33 PM
 *
 */
public class CollectionUtil {
	/**
	 * 禁止构造.
	 */
	private CollectionUtil() {
	}

	/**
	 * 比价两个List中包含的元素是否完全相同(不管顺序). <b>注意:</b>如果同时还要比较顺序是否一致, 直接用List的
	 * <code>equals</code>方法即可.
	 * 
	 * @see List#equals(Object)
	 */
	public static boolean equalsRegardlessOfOrder(List<? extends Object> lst,
			List<? extends Object> lst2) {
		if (lst == null || lst2 == null) {
			return lst == lst2;
		}

		return (lst.containsAll(lst2) && lst2.containsAll(lst));
	}

	/**
	 * 获取给定List中最后几个元素构成的子List.
	 * 
	 * @param origin
	 *            原List
	 * @param count
	 *            如果小于等于0则返回原List即<code>origin</code>。
	 * @return 子List
	 */
	public static List<?> lastSubList(List<?> origin, int count) {
		if (origin == null || count <= 0) {
			return origin;
		}
		if (origin.size() < count) {
			count = origin.size();
		}
		return origin.subList(origin.size() - count, origin.size());
	}

	/**
	 * 获取给定List中前几个元素构成的子List.
	 * 
	 * @return 前<code>count</code>个元素构成的子List; 如果<code>count</code>小于等于0或者大于
	 *         <code>origin</code>的长度，则返回原List即<code>origin</code>.
	 */
	public static List<?> subList(List<?> origin, int count) {
		if (origin == null || count <= 0) {
			return origin;
		}
		if (origin.size() < count) {
			count = origin.size();
		}
		return origin.subList(0, count);
	}

	/**
	 * 
	 * @param output
	 * @param count
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String lastSublines(List<String> output, int count) {
		// liushen@Jan 27, 2010: 必须强制转型，否则编译通不过
		List<String> lastLines = (List<String>) lastSubList(output, count);
		return toLines(lastLines);
	}

	/**
	 * 
	 * @param output
	 * @param count
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String sublines(List<String> output, int count) {
		List<String> lastLines = (List<String>) subList(output, count);
		return toLines(lastLines);
	}

	/**
	 * 转换为友好显示的字符串.
	 * 
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return 友好显示Map内容的字符串.
	 */
	public static <K, V> String toString(Map<K, V> map) {
		if (map == null) {
			return "null";
		}
		int maxIndex = map.size() - 1;
		if (maxIndex < 0) {
			return "{}";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(maxIndex + 1).append(
				" elements in " + map.getClass().getName() + ": {");
		int i = 0;
		for (K key : map.keySet()) {
			sb.append(EnvConst.NEWLINE);
			i++;
			sb.append(i).append(":\t").append(key).append(" = ")
					.append(map.get(key));
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 转换为友好显示的字符串.
	 * 
	 * @param <T>
	 * @param list
	 * @return 友好显示Collection内容的字符串.
	 */
	public static <T> String toString(Collection<T> list) {
		if (list == null) {
			return "null";
		}
		int maxIndex = list.size() - 1;
		if (maxIndex < 0) {
			return "{}";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(maxIndex + 1).append(
				" elements in " + list.getClass().getName() + ": {");
		int i = 0;
		for (T t : list) {
			i++;
			sb.append(EnvConst.NEWLINE);
			sb.append(i).append(":\t").append(t);
		}
		sb.append("}");
		return sb.toString();

	}

	/**
	 * 将若干字符串组成的List拼成一个字符串，并且最后一行没有行分隔符.
	 * 
	 * @param lastLines
	 *            字符串组成的List
	 * @return 多行组成的字符串.
	 */
	private static String toLines(List<String> lastLines) {
		if (lastLines == null) {
			return null;
		}
		final int size = lastLines.size();
		if (size == 0) {
			return null;
		}
		StringBuilder sb = new StringBuilder(80 * size);
		if (size == 1) {
			sb.append(lastLines.get(0));
			return sb.toString();
		}
		for (int i = 1; i < size; i++) {
			sb.append(EnvConst.NEWLINE).append(lastLines.get(i));
		}
		return sb.toString();
	}

	/**
	 * 判断集合是否为空.
	 * 
	 * @param objs
	 *            集合
	 * @return 集合为<code>null</code>或size为0则返回<code>true</code>
	 */
	public static boolean isEmpty(Collection<?> objs) {
		return objs == null || objs.size() == 0;
	}

	/**
	 * 判断集合是否不为空.
	 * 
	 * @param objs
	 *            集合
	 * @return 集合不为空返回<code>true</code>.
	 */
	public static boolean isNotEmpty(Collection<?> objs) {
		return objs != null && objs.size() > 0;
	}

	/**
	 * 判断Map是否为空.
	 * 
	 * @param map
	 *            Map对象
	 * @return 为<code>null</code>或size为0则返回<code>true</code>
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.size() == 0;
	}

	/**
	 * 判断Map是否非空.
	 * 
	 * @param map
	 *            Map对象
	 * @return 不为<code>null</code>、且size大于0返回<code>true</code>
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	/**
	 * 从originList中删除给定范围的子列表，并弹出删除的部分.
	 * 
	 * @param <T>
	 * @param originList
	 * @param fromIndex
	 * @param toIndex
	 * @return 子列表
	 */
	public static <T> List<T> popSubList(List<T> originList, int fromIndex,
			int toIndex) {
		List<T> subList = originList.subList(fromIndex, toIndex);
		List<T> result = new ArrayList<T>(subList);
		subList.clear();
		return result;
	}

	/**
	 * 弹出第一段匹配fromObj到toObj的部分；此方法为便捷方法，在{@link #popSubList(List, int, int)}
	 * 方法的基础上，省去调用方对index的计算.
	 * 
	 * @param <T>
	 * @param originList
	 * @param fromObj
	 * @param toObj
	 * @return 如果originList没有能匹配fromObj到toObj的部分，则返回<code>null</code>.
	 * @see #popSubList(List, int, int)
	 */
	public static <T> List<T> popFirstSubList(List<T> originList, T fromObj,
			T toObj) {
		int fromIndex = originList.indexOf(fromObj);
		if (fromIndex == -1) {
			return null;
		}
		int toIndex = originList.indexOf(toObj) + 1;
		if (toIndex == 0) {
			return null;
		}
		return popSubList(originList, fromIndex, toIndex);
	}

	/**
	 * 获取所有匹配fromObj到toObj的部分.
	 * 
	 * @param <T>
	 * @param originList
	 * @param fromObj
	 * @param toObj
	 * @return
	 */
	public static <T> List<List<T>> popAllSubList(List<T> originList,
			T fromObj, T toObj) {
		List<List<T>> result = new ArrayList<List<T>>();
		for (List<T> subList = popFirstSubList(originList, fromObj, toObj); isNotEmpty(subList); 
				subList = popFirstSubList(originList, fromObj, toObj)) {
			result.add(subList);
		}
		return result;
	}

	/**
	 * @param map
	 * @param key
	 * @param defVal
	 *            默认值
	 * @return
	 */
	public static <K, V> int getValueAsInt(Map<K, V> map, String key, int defVal) {
		if (isEmpty(map)) {
			return defVal;
		}
		V val = map.get(key);
		return NumberUtil.parseInt(val, defVal);
	}

	/**
	 * 将原始整数对象转换成对象集合
	 * 
	 * @param collectionIds 待转换的整数原类型数组
	 * @return 转换后的整数数组
	 */
	public static List<Integer> toCollection(int[] intIds) {
		List<Integer> integerIds = new ArrayList<Integer>();
		for ( int intId : intIds ){
			integerIds.add(new Integer(intId));
		}
		return integerIds;
	}

	/**
	 * 将对象数组转换成对象集合
	 * 
	 * @param objects
	 *            待转换的对象数组
	 * @return 转换后的对象集合
	 */
	public static <T> List<T> toCollection(T[] objects) {
		List<T> destObjects = new ArrayList<T>();
		for (T object : objects) {
			destObjects.add(object);
		}
		return destObjects;
	}

	// move from mas/WEB_INF/jsps/front/index.jsp; to be enhance!
	<T> T getList(List<T> list, int index) {
		if (0 == list.size()) {
			return null;
		} else {
			return list.get(index);
		}
	}
}
