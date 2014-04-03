/**
 * Title:		luoka
 */
package com.luoka.commons;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 数组相关操作工具类.<br>
 * @since zengqingmeng @ Apr 3, 2014 9:23:38 PM
 *
 */
public class ArrayUtil {
	/**
	 * 将int数组转换为Integer数组.
	 * 
	 * @param values
	 *            int数组
	 * @return Integer数组
	 */
	public static Integer[] toIntegerArray(int[] values) {
	    if (values == null) {
	        return null;
	    }
	    Integer[] result = new Integer[values.length];
	    for (int i = 0; i < values.length; i++) {
	        result[i] = new Integer(values[i]);
	    }
	    return result;
	}

	/**
	 * 将boolean数组转换为Boolean数组.
	 * 
	 * @param values
	 *            boolean数组
	 * @return Boolean数组
	 */
	public static Object[] toBooleanArray(boolean[] values) {
		if (values == null) {
			return null;
		}
		Boolean[] result = new Boolean[values.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = new Boolean(values[i]);
		}
		return result;
	}

	/**
	 * 将Integer数组转换为int数组.
	 * 
	 * @param values
	 *            Integer数组
	 * @return int数组
	 */
	public static int[] toIntArray(Integer[] values) {
	    if (values == null) {
	        return null;
	    }
	    int[] result = new int[values.length];
	    for (int i = 0; i < values.length; i++) {
	        result[i] = values[i].intValue();
	    }
	    return result;
	}

	/**
	 * 将String数组转换(parseInt)为int数组, 如果某String parse失败，则跳过该元素.
	 * 
	 * @param values
	 *            String数组
	 * @return int数组， 长度是原String数组中能够转换为int的数目.
	 */
	public static int[] toIntArray(String[] values) {
	    if (values == null) {
	        return null;
	    }

	    /*
		int[] result = new int[values.length];
	    for (int i = 0; i < values.length; i++) {
			try {
				result[i] = Integer.parseInt(values[i]);
			} catch (Exception e) {
				result[i] = 0;
			}
	    }
		return result;
		*/
	    List<Integer> temps = new ArrayList<Integer>();
	    for (int i = 0; i < values.length; i++) {
	    	try {
				temps.add(Integer.valueOf(values[i]));
			} catch (Exception e) {
			}
	    }
		return toIntArray(temps.toArray(new Integer[0]));
	}

	/**
	 * 将Map的Key转换成字符串数组
	 * 
	 * @param map
	 * @return
	 * @creator changpeng @ 2009-5-31
	 */
	public static String[] keysToString(Map<String, ? extends Object> map) {
		return map.keySet().toArray(new String[0]);
	}

	/**
	 * 将List<String>转换为字符串数组
	 * 
	 * @param list
	 * @return
	 */
	public static String[] listToString(List<String> list) {
		return list.toArray(new String[0]);
	}

	/**
	 * 将List<Integer>转换为intArray
	 * 
	 * @param list
	 * @return int[]
	 */
	public static int[] toIntArray(List<Integer> list) {
		int[] intArray = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			intArray[i] = list.get(i);
		}
		return intArray;
	}

	/**
	 * 比较两个int数组是否相等，顺序无关。
	 * 
	 * @param int[],int[]
	 * @return true表示相同，false表示不同
	 */
	public static boolean compareArrayValue(int[] a, int[] b) {
		if ((a.length == b.length) && (a.length != 0)) {
			for (int i = 0; i < a.length; i++) {
				boolean flag = false;
				for (int j = 0; j < b.length; j++) {
					if (a[i] == b[j])
						flag = true;
				}
				if (flag == false)
					return false;
			}
			return true;
		}
		else
			return false;
	}

	/**
	 * 获取数组A中比数组B中多出来的值。
	 * 
	 * @param int[],int[]
	 * @return int[]
	 */
	public static int[] getArrayValuesExceeded(int[] a, int[] b) {
		List<Integer> arrayValuesExceededIntegerList = new ArrayList<Integer>();
		for (int i = 0; i < a.length; i++) {
			boolean flag = false;
			for(int j = 0; j < b.length; j ++){
				if (a[i] == b[j])
					flag = true;
			}
			if(flag == false)
				arrayValuesExceededIntegerList.add(a[i]);
		}
		return toIntArray(arrayValuesExceededIntegerList);
	}

	/**
	 * 给定数组中是否包含所给元素.
	 * 
	 * @param anArray
	 *            数组
	 * @param anElement
	 *            元素
	 * @return 包含返回<code>true</code>
	 */
	public static <T> boolean contain(T[] anArray, T anElement) {
		if (anArray == null) {
			return false;
		}
		for (int i = 0; i < anArray.length; i++) {
			if (anArray[i].equals(anElement)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 所给字符串, 是否是以给定字符串数组的某一个元素为起始.
	 * 
	 * @param anArray
	 *            给定字符串数组
	 * @param anElement
	 *            所给字符串
	 * @return
	 */
	public static boolean prefixMatch(String[] anArray, String anElement) {
		if (anArray == null) {
			return false;
		}
		for (int i = 0; i < anArray.length; i++) {
			// 不能排除掉空格等空串.
			if (anArray[i] == null || anArray.length == 0) {
				continue;
			}
			if (anElement.startsWith(anArray[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 友好显示数组对象.
	 * 
	 * @return 友好显示该数组的字符串
	 */
	public static String toString(Object[] aryObjects) {
		if (aryObjects == null) {
			return "null";
		}
		int maxIndex = aryObjects.length - 1;
		if (maxIndex < 0) {
			return "[]";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(maxIndex + 1).append(" elements: [");
		sb.append(EnvConst.NEWLINE);
		for (int i = 0; i < maxIndex; i++) {
			sb.append(i).append(":").append(aryObjects[i]);
			sb.append(EnvConst.NEWLINE);
		}
		sb.append(maxIndex).append(":").append(aryObjects[maxIndex]);
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 友好显示数组对象.
	 * 
	 * @return 友好显示该数组的字符串
	 */
	public static String toString(int[] values) {
		return toString(toIntegerArray(values));
	}

	/**
	 * 友好显示数组对象.
	 * 
	 * @return 友好显示该数组的字符串
	 */
	public static String toString(boolean[] values) {
		return toString(toBooleanArray(values));
	}

	/**
	 * 构造一个由连续自然数(即正整数， 从1开始)构成的数组.
	 * 
	 * @param length
	 *            数组长度
	 * @return 连续自然数(即正整数， 从1开始)构成的数组
	 */
	public static int[] createSequencedPositiveIntArray(int length) {
		// AssertUtil.positive(length);
		int[] result = new int[length];
		for (int i = 0; i < result.length; i++) {
			result[i] = i + 1;
		}
		return result;
	}

	/**
	 * 扩充对象型数组.
	 * 
	 * @param <T>
	 *            数组包含的对象的类型
	 * @param srcArray
	 *            源数组
	 * @param deltaArray
	 *            要补充的数组
	 * @return 扩充后的数组
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] expand(T[] srcArray, T[] deltaArray) {
		AssertUtil.notNull(srcArray, "srcArray is null.");
		AssertUtil.notNull(deltaArray, "deltaArray is null.");
		int originLength = srcArray.length;
		Object destArray = Array.newInstance(srcArray.getClass()
				.getComponentType(), originLength + deltaArray.length);
		System.arraycopy(srcArray, 0, destArray, 0, originLength);
		System.arraycopy(deltaArray, 0, destArray, originLength,
				deltaArray.length);
		return (T[]) destArray;
	}

	/**
	 * 扩充整型数组.
	 * 
	 * @param srcArray
	 *            源数组
	 * @param deltaArray
	 *            要补充的数组
	 * @return 扩充后的数组
	 */
	public static int[] expand(int[] srcArray, int[] deltaArray) {
		AssertUtil.notNull(srcArray, "srcArray is null.");
		AssertUtil.notNull(deltaArray, "deltaArray is null.");
		int originLength = srcArray.length;
		Object destArray = Array.newInstance(srcArray.getClass()
				.getComponentType(), originLength + deltaArray.length);
		System.arraycopy(srcArray, 0, destArray, 0, originLength);
		System.arraycopy(deltaArray, 0, destArray, originLength,
				deltaArray.length);
		return (int[]) destArray;
	}

	/**
	 * 比较两个一维数组是否相同(顺序需相同); 本方法不适用于二维或更高维数组的比较.
	 * 
	 * @param oneArray
	 *            一个一维数组
	 * @param anotherArray
	 * @return
	 */
	public static <T> boolean equals(T[] oneArray, T[] anotherArray) {
		return Arrays.equals(oneArray, anotherArray);
	}

	/**
	 * 比价两个一维数组包含的元素是否相同(不管顺序).
	 * 
	 * @param <T>
	 * @param oneArray
	 * @param anotherArray
	 * @return
	 */
	public static <T> boolean equalsIgnoreOrder(T[] oneArray, T[] anotherArray) {
		List<T> oneList = Arrays.asList(oneArray);
		List<T> anotherList = Arrays.asList(anotherArray);
		return CollectionUtil.equalsRegardlessOfOrder(oneList, anotherList);
	}

	/**
	 * @param objArray
	 * @return
	 */
	public static boolean isEmpty(Object[] objArray) {
		return (objArray == null || objArray.length == 0);
	}
}
