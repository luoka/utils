/**
 * Title:		luoka
 */
package com.luoka.commons;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * 字符串处理的工具类, 由于历史原因该类以Helper命名, 而不是以Util命名. <BR>
 * @since zengqingmeng @ Apr 3, 2014 8:42:59 PM
 *
 */
public class StringHelper {
	/**
	 * 常量，值为空串<code>""</code>.
	 */
	public static final String EMPTY = "";

	/**
	 * 将指定字符串的指定位置的字符以byte方式返回. 该方法中对返回的字符有一次ascii转换, 即减去一个'0'的ascii值(十进制的48).
	 * 
	 * @param strHead
	 *            指定字符串
	 * @param index
	 *            指定位置
	 * @return 指定位置的字符的byte值
	 */
	public static byte byteAt(String strHead, int index) {
		byte result = (byte) strHead.charAt(index);
		result -= '0';
		return result;
	}

	/**
	 * 转换html标签(<,>)
	 * 
	 * @param src
	 * @return
	 */
	public static String escape(String src) {
		src = src.replaceAll("<", "&lt;");
		src = src.replaceAll(">", "&gt;");
		return src;
	}
	
	/**
	 * 将给定字符串(origin)以字符串token作为分隔符进行分隔, 得到一个字符串数组. 该函数不依赖于JDK 1.4, 和JDK
	 * 1.4中String.split(String regex)的区别是不支持正则表达式.<br>
	 * 在不包含有token字符串时, 本函数返回以原字符串构成的数组.
	 * 
	 * @param origin
	 *            给定字符串
	 * @param token
	 *            分隔符
	 * @return 字符串数组
	 */
	public static String[] split(String origin, String token) {
		if (origin == null) {
			return null;
		}
		StringTokenizer st = (token == null) ? new StringTokenizer(origin)
				: new StringTokenizer(origin, token);
		final int countTokens = st.countTokens();
		if (countTokens <= 0) {
			return new String[] { origin };
		}
		String[] results = new String[countTokens];
		for (int i = 0; i < countTokens; i++) {
			results[i] = st.nextToken();
		}
		return results;
	}

	/**
	 * 得到给定字符串的逆序的字符串. <BR>
	 * 用例: <code>
	 * <pre>
	 *         assertEquals("/cba", StringHelper.reverse("abc/"));
	 *         assertEquals("aabbbccccx", StringHelper.reverse("xccccbbbaa"));
	 * 		   assertEquals("试测^%6cbA数参", StringHelper.reverse("参数Abc6%^测试"));
	 * </pre>
	 * </code>
	 */
	public static String reverse(String origin) {
		if (origin == null) {
			return null;
		}
		return new StringBuilder(origin).reverse().toString();
	}

	/**
	 * 用ISO-8859-1对给定字符串编码.
	 * 
	 * @param string
	 *            给定字符串
	 * @return 编码后所得的字符串. 如果给定字符串为null或"", 则原样返回.
	 */
	public static String encodingByISO8859_1(String string) {
		if ((string != null) && !("".equals(string))) {
			try {
				return new String(string.getBytes(), "ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				return string;
			}
		}
		return string;
	}

	/**
	 * The method is identical with {@link #split(String, String)}, and will be
	 * deleted.
	 * 
	 * @deprecated : 重复的方法，将删除; 应该使用
	 *             {@link #split(String, String)}
	 */
	@Deprecated
	public static String[] splitAlways(String origin, String token) {
		return split(origin, token);
	}

	// from commons-codec: char[] Hex.encodeHex(byte[])
	/**
	 * 将给定的字节数组用十六进制字符串表示.
	 */
	public static String toString(byte[] data) {
		if (data == null) {
			return "null!";
		}
		int l = data.length;

		char[] out = new char[l << 1];

		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS[0x0F & data[i]];
		}

		return new String(out);
	}

	// from commons-codec: byte[] Hex.decodeHex(char[])
	/**
	 * 将给定的十六进制字符串转化为字节数组. <BR>
	 * 与<code>toString(byte[] data)</code>作用相反.
	 * 
	 * @throws RuntimeException
	 *             当给定十六进制字符串的长度为奇数时或给定字符串包含非十六进制字符.
	 * @see #toString(byte[])
	 */
	public static byte[] toBytes(String str) {
		if (str == null) {
			return null;
		}
		char[] data = str.toCharArray();
		int len = data.length;

		if ((len & 0x01) != 0) {
			throw new RuntimeException("Odd number of characters!");
		}

		byte[] out = new byte[len >> 1];

		// two characters form the hex value.
		for (int i = 0, j = 0; j < len; i++) {
			int f = toDigit(data[j], j) << 4;
			j++;
			f = f | toDigit(data[j], j);
			j++;
			out[i] = (byte) (f & 0xFF);
		}

		return out;
	}

	/**
	 * HTML元素value值过滤处理函数：将 <code> & &lt; &gt; &quot </code> 等特殊字符作转化处理. <BR>
	 * 用例: <code>
	 *    &lt;input type="text" name="Name" value="<%=StringHelper.filterForHTMLValue(sContent)%>"&gt;
	 * </code>
	 * 
	 * @param _sContent
	 *            指定的文本内容. 如果为null则返回"".
	 * @return 处理后的文本内容.
	 */
	public static String filterForHTMLValue(String _sContent) {
		if (_sContent == null)
			return "";

		// _sContent = replaceStr(_sContent,"</script>","<//script>");
		// _sContent = replaceStr(_sContent,"</SCRIPT>","<//SCRIPT>");

		char[] srcBuff = _sContent.toCharArray();
		int nLen = srcBuff.length;
		if (nLen == 0)
			return "";

		StringBuffer retBuff = new StringBuffer((int) (nLen * 1.8));

		for (int i = 0; i < nLen; i++) {
			char cTemp = srcBuff[i];
			switch (cTemp) {
			case '&': // 转化：& -->&amp;why: 2002-3-19
				// caohui@0515
				// 处理unicode代码
				if ((i + 1) < nLen) {
					cTemp = srcBuff[i + 1];
					if (cTemp == '#')
						retBuff.append("&");
					else
						retBuff.append("&amp;");
				} else
					retBuff.append("&amp;");
				break;
			case '<': // 转化：< --> &lt;
				retBuff.append("&lt;");
				break;
			case '>': // 转化：> --> &gt;
				retBuff.append("&gt;");
				break;
			case '\"': // 转化：" --> &quot;
				retBuff.append("&quot;");
				break;
			default:
				retBuff.append(cTemp);
			}// case
		}// end for

		return retBuff.toString();
	}

	/**
	 * 等价于<code>toString(objs, false, ",");</code>.
	 */
	public static String toString(Object[] objs) {
		return toString(objs, false, ", ");
	}

	/**
	 * 等价于<code>toString(objs, showOrder, ",");</code>.
	 * 
	 * @see #toString(Object[], boolean, String)
	 */
	public static String toString(Object[] objs, boolean showOrder) {
		return toString(objs, showOrder, ",");
	}

	/**
	 * 输出数组内容. 如果数组为null, 返回null. 如果数组某元素为null则该元素输出为null.
	 * 
	 * @param objs
	 *            待输出的数组
	 * @param showOrder
	 *            是否输出元素的序号
	 * @param token
	 *            元素间的分割串
	 */
	public static String toString(Object[] objs, boolean showOrder, String token) {
		if (objs == null) {
			return "null";
		}
		int len = objs.length;
		StringBuffer sb = new StringBuffer(10 * len);
		for (int i = 0; i < len; i++) {
			if (showOrder) {
				sb.append(i).append(':');
			}
			sb.append(objs[i]);
			if (i < len - 1) {
				sb.append(token);
			}
		}
		return sb.toString();
	}

	public static String avoidNull(String str) {
		return (str == null) ? "" : str;
	}

	/**
	 *
	 * @param str
	 * @return
	 */
	public static String assertNotEmptyAndTrim(String str) {
		AssertUtil.notNullOrEmpty(str, "the string is empty!");
		return str.trim();
	}

	/**
	 * 为空或null时返回缺省值
	 * 
	 * @param str
	 * @param defaultStr
	 * @return
	 * @see #isEmpty(String)
	 */
	public static String avoidEmpty(String str, String defaultStr) {
		return isEmpty(str) ? defaultStr : str;
	}

	/**
	 * 以","为分隔符连接数组为一个串
	 * 
	 * @param array
	 * @return
	 * @see StringHelper.toString(Object[] array)
	 */
	public static String join(int[] array, String separator) {
		if (array == null) {
			return null;
		}
		if (separator == null) {
			separator = EMPTY;
		}

		StringBuilder buf = new StringBuilder(200);

		if (array.length > 0) {
			buf.append(array[0]);
		}
		for (int i = 1; i < array.length; i++) {
			buf.append(separator);
			buf.append(array[i]);
		}
		return buf.toString();
	}

	/**
	 * 以","为分隔符连接数组为一个串
	 * 
	 * @param array
	 * @return
	 * @see StringHelper.toString(Object[] array)
	 */
	public static String join(Object[] array) {
		return join(array, ",");
	}

	/**
	 * 指定分隔符用来连接数组为一个串
	 * 
	 * @param array
	 * @param separator
	 * @return
	 */
	public static String join(Object[] array, char separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}

	/**
	 * 指定分隔符用来连接数组为一个串
	 * 
	 * @param array
	 * @param separator
	 * @return
	 */
	public static String join(Object[] array, String separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing
	 * the provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty
	 * strings within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * join(null, *)               = null
	 * join([], *)                 = ""
	 * join([null], *)             = ""
	 * join(["a", "b", "c"], ';')  = "a;b;c"
	 * join(["a", "b", "c"], null) = "abc"
	 * join([null, "", "a"], ';')  = ";;a"
	 * </pre>
	 *
	 * @param array
	 *            the array of values to join together, may be null
	 * @param separator
	 *            the separator character to use
	 * @param startIndex
	 *            the first index to start joining from. It is an error to pass
	 *            in an end index past the end of the array
	 * @param endIndex
	 *            the index to stop joining from (exclusive). It is an error to
	 *            pass in an end index past the end of the array
	 * @return the joined String, <code>null</code> if null array input
	 * @see Apache Commons Lang
	 */
	static String join(Object[] array, char separator, int startIndex,
			int endIndex) {
		if (array == null) {
			return null;
		}
		int bufSize = (endIndex - startIndex);
		if (bufSize <= 0) {
			return EMPTY;
		}

		bufSize *= ((array[startIndex] == null ? 16 : array[startIndex]
				.toString().length()) + 1);
		StringBuffer buf = new StringBuffer(bufSize);

		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	static String join(Object[] array, String separator, int startIndex,
			int endIndex) {
		if (array == null) {
			return null;
		}
		if (separator == null) {
			separator = EMPTY;
		}

		// endIndex - startIndex > 0: Len = NofStrings *(len(firstString) +
		// len(separator))
		// (Assuming that all Strings are roughly equally long)
		int bufSize = (endIndex - startIndex);
		if (bufSize <= 0) {
			return EMPTY;
		}

		bufSize *= ((array[startIndex] == null ? 16 : array[startIndex]
				.toString().length()) + separator.length());

		StringBuffer buf = new StringBuffer(bufSize);

		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	/**
	 * Capitalize a <code>String</code>, changing the first letter to upper case
	 * as per {@link Character#toUpperCase(char)}. No other letters are changed.
	 *
	 * @param str
	 *            the String to capitalize, may be <code>null</code>
	 * @return the capitalized String, <code>null</code> if null
	 */
	public static String capitalize(String str) {
		return changeFirstCharacterCase(str, true);
	}

	/**
	 * Uncapitalize a <code>String</code>, changing the first letter to lower
	 * case as per {@link Character#toLowerCase(char)}. No other letters are
	 * changed.
	 *
	 * @param str
	 *            the String to uncapitalize, may be <code>null</code>
	 * @return the uncapitalized String, <code>null</code> if null
	 */
	public static String uncapitalize(String str) {
		return changeFirstCharacterCase(str, false);
	}

	private static String changeFirstCharacterCase(String str,
			boolean capitalize) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str.length());
		if (capitalize) {
			buf.append(Character.toUpperCase(str.charAt(0)));
		} else {
			buf.append(Character.toLowerCase(str.charAt(0)));
		}
		buf.append(str.substring(1));
		return buf.toString();
	}

	/**
	 * 去除字符串首尾的空白字符。
	 * 
	 * @param str
	 *            原字符串
	 * @return 处理后的字符串；如果原字符串为<code>null</code>则也返回<code>null</code>.
	 */
	public static String trim(String str) {
		return (str == null) ? null : str.trim();
	}

	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * Converts a hexadecimal character to an integer.
	 *
	 * @param ch
	 *            A character to convert to an integer digit
	 * @param index
	 *            The index of the character in the source
	 * @return An integer
	 * @throws RuntimeException
	 *             Thrown if ch is an illegal hex character
	 */
	static int toDigit(char ch, int index) {
		int digit = Character.digit(ch, 16);
		if (digit == -1) {
			throw new RuntimeException("Illegal hexadecimal charcter " + ch
					+ " at index " + index);
		}
		return digit;
	}

	/**
	 * 按给定分割符对给定字符串作分割, 然后作trim处理.<BR>
	 * <li>origin为null时返回null. <li>不包含有token字符串时, 本函数返回以原字符串trim后构成的数组.
	 * 
	 * @param origin
	 *            给定字符串
	 * @param token
	 *            分隔符. 不允许为null.
	 * @return 字符串数组
	 */
	public static String[] splitAndTrim(String origin, String token) {
		if (origin == null) {
			return null;
		}
		origin = origin.trim();
		final StringTokenizer st = new StringTokenizer(origin, token);
		final int countTokens = st.countTokens();
		if (countTokens <= 0) {
			return new String[] { origin };
		}
		List<String> strs = new ArrayList<String>(countTokens);
		String str;
		for (int i = 0; i < countTokens; i++) {
			str = st.nextToken().trim();
			if (str.length() > 0) {
				strs.add(str);
			}
		}
		return strs.toArray(new String[0]);
	}

	public static String hexToStr(String hex) {
		return new String(toBytes(hex));
	}

	public static String truncateAndTrim(String str, String delim) {
		if (str == null || delim == null) {
			return str;
		}
		int nStart = str.indexOf(delim);
		if (nStart < 0) {
			return str;
		}
		return str.substring(nStart + delim.length()).trim();
	}

	/**
	 * 获得字符串指定编码的字符串，默认的原始编码为ISO8859-1。
	 * 
	 * @param originalStr
	 *            原始的串
	 * @param encoding
	 *            目标编码
	 * @return 编码后的字符串
	 */
	public static String getStringByEncoding(String originalStr, String encoding) {
		return getStringByEncoding(originalStr, "ISO8859-1", encoding);
	}

	/**
	 * 获得字符串指定编码的字符串.
	 * 
	 * @param str
	 *            原始的串
	 * @param fromEncoding
	 *            原始的编码
	 * @param toEncoding
	 *            目标编码
	 * @return 编码后的字符串。如果原始串为<code>null</code>，则也返回<code>null</code>.
	 */
	public static String getStringByEncoding(String str,
			String fromEncoding, String toEncoding) {
		if (str == null) {
			return null;
		}
		if (StringHelper.isEmpty(fromEncoding)) {
			return str;
		}
		try {
			return new String(str.getBytes(fromEncoding),
					toEncoding);
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}

	/**
	 * 判断字符串是否为null或空.
	 * 
	 * <pre>
	 * isEmpty(null)      = true
	 * isEmpty(&quot;&quot;)        = true
	 * isEmpty(&quot; &quot;)       = true
	 * isEmpty(&quot;bob&quot;)     = false
	 * isEmpty(&quot;  bob  &quot;) = false
	 * </pre>
	 * 
	 * @return true if <code>(str == null || str.trim().length() == 0)</code>,
	 *         otherwise false.
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.trim().length() == 0);
	}

	/**
	 * 将字符串调整为不超过maxLength长度的字符串, 调整方法为截掉末尾适当长度, 并且以...结束.
	 * 
	 * @param str
	 * @param maxLength
	 * @return
	 */
	public static String truncate(String str, int maxLength) {
		if (str == null) {
			return str;
		}
		if (maxLength <= 0) {
			throw new IllegalArgumentException("Illegal value of maxLength: "
					+ maxLength + "! It must be a positive integer.");
		}
		int strLength = str.length();
		if (maxLength >= strLength) {
			return str;
		}
		final String DELIT = "...";
		StringBuilder sb = new StringBuilder(maxLength);
		int splitPos = maxLength - DELIT.length();
		sb.append(str.substring(0, splitPos));
		sb.append(DELIT);
		return sb.toString();
	}

	/**
	 * 将字符串调整为不超过maxLength长度的字符串, 调整方法为去掉中间的适当长度, 以...区分开头和结束.
	 * 
	 * @param maxLength
	 *            指定的长度.
	 * @see StringHelperTest#testAdjustLength()
	 */
	public static String adjustLength(String str, int maxLength) {
		if (str == null) {
			return str;
		}
		if (maxLength <= 0) {
			throw new IllegalArgumentException("Illegal value of maxLength: "
					+ maxLength + "! It must be a positive integer.");
		}
		int strLength = str.length();
		if (maxLength > strLength) {
			return str;
		}
		final String DELIT = "...";
		StringBuilder sb = new StringBuilder(maxLength);
		int splitPos = (maxLength - DELIT.length()) / 2;
		sb.append(str.substring(0, splitPos));
		sb.append(DELIT);
		sb.append(str.substring(strLength - splitPos));
		return sb.toString();
	}

	/**
	 * 获得安全的URL，避免跨站式攻击 处理方法同
	 * {@link RequestUtil#getParameterSafe(javax.servlet.http.HttpServletRequest, String)}
	 * 
	 * @return
	 */
	public static String getURLSafe(String url) {
		if (url == null || "".equals(url))
			return "";

		StringBuffer strBuff = new StringBuffer();
		char[] charArray = url.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if (charArray[i] == '<' || charArray[i] == '>')
				continue;

			strBuff.append(charArray[i]);
		}
		return strBuff.toString();
	}

	/**
	 * 去掉末尾的/.
	 * 
	 * @param str
	 * @return
	 */
	public static String removeLastSlashChar(String str) {
		if (str == null) {
			return null;
		}
		if (str.endsWith("/")) {
			return str.substring(0, str.length() - 1);
		}
		return str;
	}

	/**
	 * 在末尾添加/, 如果不是以/结尾的话.
	 * 
	 * @param str
	 *            原字符串
	 * @see #smartAppendSuffix(String, String)
	 * @see #removeLastSlashChar(String)
	 */
	public static String smartAppendSlashToEnd(String str) {
		return smartAppendSuffix(str, "/");
	}

	/**
	 * 在原字符串末尾添加给定的后缀, 如果不是以该后缀结尾的话.
	 * 
	 * @param str
	 *            原字符串
	 * @param endingStr
	 *            后缀(用来结束的字符或字符串)
	 * @return 原字符串(如果原字符串为<code>null</code>或已经以该后缀结束)；或者原字符串加上该后缀
	 */
	public static String smartAppendSuffix(String str, String endingStr) {
		if (str == null) {
			return null;
		}
		return str.endsWith(endingStr) ? str : str + endingStr;
	}

	/**
	 * 字符串替换, 不基于正则表达式, 并且适用于JDK1.3. 各种情况的返回值参见测试用例:
	 * {@link LangUtilTest#testReplaceAll()} in Util_JDK14.
	 * 
	 * @param origin
	 *            源字符串
	 * @param oldPart
	 *            被替换的旧字符串
	 * @param replacement
	 *            用来替换旧字符串的新字符串
	 * @return 替换处理后的字符串
	 */
	public static String replaceAll(String origin, String oldPart,
			String replacement) {
		if (origin == null || replacement == null) {
			return origin;
		}
		// 必须排除oldPart.length() == 0(即oldPart为"")的情况, 因为对任何字符串s, s.indexOf("")
		// == 0而非-1!
		if (oldPart == null || oldPart.length() == 0) {
			return origin;
		}

		int index = origin.indexOf(oldPart);
		if (index < 0) {
			return origin;
		}

		StringBuffer sb = new StringBuffer(origin);
		do {
			sb.replace(index, index + oldPart.length(), replacement);
			origin = sb.toString();
			index = origin.indexOf(oldPart);
		} while (index != -1);
		return origin;
	}

	/**
	 * 从该字符串中去掉指定的起始部分.
	 * 
	 * @param origin
	 *            该字符串
	 * @param beginPart
	 *            指定的起始部分
	 * @return 该字符串中去掉指定的起始部分后的内容; 如果不以其起始, 则返回原串.
	 */
	public static String removeBeginPart(String origin, String beginPart) {
		if (origin == null || beginPart == null) {
			return origin;
		}

		int pos = origin.indexOf(beginPart);
		if (pos != 0) {
			return origin;
		}

		return origin.substring(beginPart.length());
	}

	/**
	 * 以,为间隔, 分隔为整型数组.
	 * 
	 * @param data
	 * @return
	 */
	public static int[] split2IntArray(String data) {
		String[] splited = splitAndTrim(data, ",");
		return ArrayUtil.toIntArray(splited);
	}

	/**
	 * 将int型转换为字符串，转换失败则返回空字符串
	 * 
	 * @param num
	 * @return
	 */
	public static String int2String(int num) {
		try {
			String str = String.valueOf(num);
			return str;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 替换java的回车为html回车元素
	 * 
	 * @param origin
	 * @return
	 */
	public static String replaceEnter2HtmlBr(String origin) {
		return replaceAll(origin, EnvConst.NEWLINE, "<br>");
	}

	/**
	 * 返回原串中位于两个字符串之间的子串，不包括这两个字符串.
	 * 
	 * @param origin
	 * @param begin
	 * @param end
	 * @return
	 */
	public static String substring(String origin, String begin, String end) {
		if (origin == null) {
			return origin;
		}
		int beginIndex = (begin == null) ? 0 : origin.indexOf(begin)
				+ begin.length();
		int endIndex = (end == null) ? origin.length() : origin.indexOf(end,
				beginIndex);
		if (endIndex == -1) {
			return origin.substring(beginIndex);
		}
		return origin.substring(beginIndex, endIndex);
	}

	/**
	 * 返回原串中位于某字符串之后的子串.
	 * 
	 * @see #substring(String, String, String)
	 */
	public static String substring(String origin, String begin) {
		return substring(origin, begin, null);
	}

	/**
	 * 提取整数；本方法和 {@link NumberUtil#parseInt(String)} 的区别是，不支持小数(舍入处理)，直接返回
	 * <code>-1</code>.
	 * 
	 * @param value
	 *            要转换的字符串
	 * @return 如果<code>Integer.parseInt</code>不能成功，则返回<code>-1</code>.
	 */
	public static int parseInt(String value) {
		if (value == null)
			return -1;
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			return -1;
		}
	}

	/**
	 * 按指定的格式提取整数.
	 * 
	 * @param source
	 *            要转换的字符串
	 * @param formatPattern
	 *            指定的格式，参见 {@link DecimalFormat}.
	 * @return 如果转换不能成功，则返回<code>-1</code>.
	 */
	public static int parseIntUsingFormat(String source, String formatPattern) {
		DecimalFormat df = new DecimalFormat(formatPattern);
		try {
			return df.parse(source).intValue();
		} catch (ParseException e) {
			return -1;
		}
	}

	/**
	 * 将字符串分割成名值对（Key-Value）
	 * 
	 * @param src
	 * @param propertyDelimiter
	 * @param fieldDelimiter
	 * @return
	 */
	public static Map<String, String> parseAsMap(String src, String propertyDelimiter,
			String fieldDelimiter) {
		Map<String, String> parsedMap = new HashMap<String, String>();
		if (StringHelper.isEmpty(src)) {
			return parsedMap;
		}
		//
		String[] properties = StringHelper.split(src, propertyDelimiter);
		for (String property : properties) {
			int keyIndex = property.indexOf(fieldDelimiter);
			if ( keyIndex != -1 ){
				String key = property.substring(0, keyIndex);
				String value = property.substring(keyIndex
						+ fieldDelimiter.length());
				parsedMap.put(key, value);
			}
		}
		return parsedMap;
	}

	/**
	 * 将字符串按照默认的分隔符切割成名值对（Key-Value），默认的属性分隔符是“;“，默认的字段分隔符是”=“
	 * 
	 * @param src
	 * @return
	 */
	public static Map<String, String> parseAsMap(String src) {
		return parseAsMap(src, ";", "=");
	}

	/**
	 * 去除首尾的双引号.
	 * 
	 * @param value
	 * @return
	 */
	public static String trimQuote(String value) {
		if (value == null) {
			return value;
		}
		value = value.trim();
		final int length = value.length();
		if (length < 2) {
			return value;
		}
		if (value.charAt(0) != '"' || value.charAt(length - 1) != '"') {
			return value;
		}
		return value.substring(1, length - 1);
	}

	/**
	 * 给包含空格的字符串增加首尾的双引号.
	 * 
	 * @param str
	 * @return
	 */
	public static String addQuote(String str) {
		if (str.indexOf(' ') > 0) {
			str = "\"" + str + "\"";
		}

		return str;
	}

	/**
	 * 判断是否为数字
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isNumeric(String input) {
		if (StringHelper.isEmpty(input))
			return false;
		Pattern pattern = Pattern.compile("^[-|+]?(\\d+)(\\.\\d+)?");
		return pattern.matcher(input).matches();
	}

	/**
	 * 判断是否为整数
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isInteger(String input) {
		if (StringHelper.isEmpty(input))
			return false;
		Pattern pattern = Pattern.compile("^[-|+]?[1-9][0-9]*");
		return pattern.matcher(input).matches();
	}

	/**
	 * 判断是否为正整数.
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isPositiveInteger(String input) {
		if (StringHelper.isEmpty(input))
			return false;
		Pattern pattern = Pattern.compile("^[1-9][0-9]*");
		return pattern.matcher(input).matches();
	}

	/**
	 * 判断输入是否手机号码或座机电话号码
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isTelphone(String input) {
		if (StringHelper.isEmpty(input))
			return false;
		Pattern pattern = Pattern
				.compile("^(((\\+|0)[0-9]{2,3})-)?((0[0-9]{2,3})-)?([0-9]{8})(-([0-9]{3,4}))?$");
		return pattern.matcher(input).matches();
	}

	/**
	 * 判断输入是否手机号码<br>
	 * 支持的格式为：+国际区号-国内区号-电话号码-分机号<br>
	 * 其中：<br>
	 * + 可选 ；国际区号2-3位数字；国内区号以0开头、3-4位数字；电话号码为7-8位数字；分机号为3-4位数字；
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isMobile(String input) {
		if (false == isNumeric(input))
			return false;
		return input.length() == 11 && input.charAt(0) == '1';
	}

	/**
	 * 判断输入是否符合账号的要求
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isAccountName(String input, int minLength,
			int maxLength) {
		if (StringHelper.isEmpty(input))
			return false;
		Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]{4,15}$");
		if (false == pattern.matcher(input).matches())
			return false;
		return limitedLength(input, minLength, maxLength);
	}

	/**
	 * 判断输入是否符合长度限制
	 * 
	 * @param input
	 * @param minLength
	 * @param maxLength
	 * @return
	 */
	public static boolean limitedLength(String input, int minLength,
			int maxLength) {
		if (StringHelper.isEmpty(input))
			return (minLength <= 0);
		return input.length() >= minLength && input.length() <= maxLength;
	}

	/**
	 * 判断输入是否为邮件地址
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isEmail(String input) {
		if (StringHelper.isEmpty(input))
			return false;
		Pattern pattern = Pattern
				.compile("^[A-Za-z0-9._%+-]+@(\\w+\\.)+[a-zA-Z]{2,3}");
		return pattern.matcher(input).matches();
	}

	/**
	 * 判断输入是否为域名
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isDomainName(String input) {
		if (StringHelper.isEmpty(input))
			return false;
		Pattern pattern = Pattern
				.compile("[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+");
		return pattern.matcher(input).matches();
	}

	/**
	 * 判断输入是否为IP地址
	 * 
	 * @param input
	 * @return
	 * @deprecated
	 */
	@Deprecated
	public static boolean isIP(String input) {
		if (StringHelper.isEmpty(input))
			return false;
		Pattern pattern = Pattern
				.compile("((25[0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]\\d)|\\d)(\\.((25[0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]\\d)|\\d)){3}");
		return pattern.matcher(input).matches();
	}

	/**
	 * 判断是否为IPv4的地址
	 * 
	 * @param input
	 * @return 如果是的话返回true，否则返回false。
	 */
	public static boolean isIPv4(String input) {
		if (StringHelper.isEmpty(input))
			return false;
		Pattern pattern = Pattern.compile("((25[0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]\\d)|\\d)(\\.((25[0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]\\d)|\\d)){3}");
		return pattern.matcher(input).matches();

	}

	/**
	 * 判断输入是否为IP地址集合
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isMultiIPs(String input) {
		if (isEmpty(input))
			return false;
		StringTokenizer st = new StringTokenizer(input, ",");
		boolean isMultiIPs = true;
		for (; st.hasMoreTokens() && isMultiIPs;) {
			String token = st.nextToken();
			isMultiIPs &= isIP(token);
		}
		return isMultiIPs;
	}

	/**
	 * 判断输入是否为HTTP URL
	 * 
	 * @param input
	 * @return
	 */
	// TODO: 该方法需要增加对https的支持。
	public static boolean isHttpUrl(String input) {
		if (StringHelper.isEmpty(input))
			return false;
		Pattern pattern = Pattern
				.compile("http://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(:\\d{2,4})?(\\/[^#$]+)*");
		return pattern.matcher(input).matches();
	}

	/**
	 * 判断输入是否为RTMP协议族URL.
	 * 
	 * @param input
	 * @return
	 */
	// TODO:  该方法需要增加对rtmps、rtmpt等的支持。
	public static boolean isRtmpUrl(String input) {
		if (StringHelper.isEmpty(input)) {
			return false;
		}
		input = removeLastSlashChar(input);
		Pattern pattern = Pattern
				.compile("rtmp://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(:\\d{2,4})?(\\/[^#$]+)*");
		return pattern.matcher(input).matches();
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isFtpUrl(String input) {
		if (isEmpty(input)) {
			return false;
		}
		URL url;
		try {
			url = new URL(input);
		} catch (MalformedURLException e) {
			return false;
		}
		return UrlUtil.isFtp(url);
	}

	/**
	 * 判断输入是否为音视频的URL；RTMP协议族、HTTP协议族等都可作为音视频的URL.
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isMediaUrl(String input) {
		return isRtmpUrl(input) || isHttpUrl(input);
	}

	/**
	 * 判断输入是否为QQ号.
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isQQ(String input) {
		if (StringHelper.isEmpty(input))
			return false;
		Pattern pattern = Pattern.compile("^[1-9]\\d{4,8}$");
		return pattern.matcher(input).matches();
	}

	/**
	 * 判断输入是否为日期
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isDate(String input) {
		if (StringHelper.isEmpty(input))
			return false;
		String eL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		return Pattern.compile(eL).matcher(input).matches();
	}

	/**
	 * 利用正则表达式替换字符串内容
	 * 
	 * @param source
	 *            源字符串
	 * @param findRegex
	 *            查找正则表达式
	 * @param replaceRegex
	 *            替换正则表达式
	 * @return 替换后的正则表达式
	 */
	public static String regexReplace(String source, String findRegex, String replaceRegex) {
		return Pattern.compile(findRegex).matcher(source).replaceAll(replaceRegex);
	}

	/**
	 * 变为用*表示的显示形式，位数和原串相等。由于密码前后均可能用空格以提高安全性，故本方法不做trim处理.
	 * 
	 * @param value
	 * @return
	 */
	public static String toSecurityMaskForm(String value) {
		if (value == null) {
			return null;
		}
		int length = value.length();
		if (length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append('*');
		}
		return sb.toString();
	}

	/**
	 * 集合使用特定的分隔符转换成字符串
	 * 
	 * @param entities
	 * @param separator
	 * @return
	 */
	public static String join(Map<String, ?> entities, String separator) {
		if (entities == null) {
			return null;
		}
		if (separator == null) {
			separator = ",";
		}
		StringBuilder buf = new StringBuilder(200);
		String[] keys = entities.keySet().toArray(new String[0]);
		for (int i = 0; i < keys.length; i++) {
			buf.append(keys[i]).append("=")
					.append(StringHelper.avoidNull(entities.get(keys[i])
							.toString())).append(separator);
		}
		return buf.toString();
	}

	/**
	 * 和 {@link #isEmpty(String)} 相反。
	 * 
	 * @since liushen @ Jul 1, 2011
	 */
	public static boolean isNotEmpty(String str) {
		return false == isEmpty(str);
	}

	/**
	 * 将String数组中的值转化为set，避免重复值
	 * 
	 * @param strArray
	 * @return
	 */
	public static Set<String> StringArrayToSet(String[] strArray) {
		if (strArray == null || strArray.length == 0)
			return null;

		Set<String> set2Return = new HashSet<String>();
		for (int i = 0; i < strArray.length; i++) {
			String str = strArray[i];
			set2Return.add(str);
		}
		return set2Return;
	}

	/**
	 * 按顺序合并两个字符数组
	 * 
	 * @param firstArray
	 * @param secondArray
	 * @return
	 * @since v3.5
	 */
	public static String[] mergeArrary(String[] firstArray, String[] secondArray) {
		if (isStringArrayEmpty(firstArray) && isStringArrayEmpty(secondArray))
			return null;
		if (isStringArrayEmpty(firstArray) && secondArray != null)
			return secondArray;
		if (firstArray != null && isStringArrayEmpty(secondArray))
			return firstArray;

		int firstLength = firstArray.length;
		int secondLength = secondArray.length;
		int resultLength = firstLength + secondLength;
		String[] resultArray = new String[resultLength];
		for (int i = 0; i < firstLength; i++) {
			resultArray[i] = firstArray[i];
		}
		for (int j = 0; j < secondLength; j++) {
			resultArray[firstLength + j] = secondArray[j];
		}
		return resultArray;
	}

	public static boolean isStringArrayEmpty(String[] strArray) {
		if (strArray == null || strArray.length == 0)
			return true;
		return false;
	}

	/**
	 * 在忽略字母大小写和前后空白的情况下，判断两个字符串是否相同。
	 * 
	 * @return 以下两种情况返回<code>true</code>，其他情况均返回<code>false</code>:
	 *         <ul>
	 *         <li>两个字符串都是<code>null</code></li>
	 *         <li>两个字符串在在忽略字母大小写和前后空白后内容相同</li>
	 *         </ul>
	 */
	public static boolean equalIngoreCaseAndSpace(String one, String another) {
		if (one == null) {
			return another == null;
		}
		if (another == null) {
			return false;
		}
		return one.trim().equalsIgnoreCase(another.trim());
	}
}
