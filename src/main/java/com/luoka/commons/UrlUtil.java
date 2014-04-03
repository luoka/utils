/**
 * Title:		luoka
 */
package com.luoka.commons;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * URL处理工具类.<br>
 * @since zengqingmeng @ Apr 3, 2014 9:29:32 PM
 *
 */
public class UrlUtil {
	/**
	 * 根据指定的url获取各级别域名
	 * 
	 * @param url
	 * @param tldLevel
	 * @return
	 */
	public static String getDomainByLevel(String serverName, int tldLevel) {
		if (StringHelper.isEmpty(serverName) || serverName.indexOf(".") < 0)
			return "";
		StringBuffer sb = new StringBuffer(20);
		String[] arr = serverName.split("\\.");
		int length = arr.length;
		if (length > 0 && arr[length - 1].matches("\\b\\d+\\b"))
			return "";
		if (tldLevel >= length) {
			sb.append(serverName);
		} else {
			for (int j = 0; j < tldLevel; j++) {
				sb.append(".");
				sb.append(arr[j + length - tldLevel]);
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param url
	 * @param paramName
	 * @param value
	 * @return
	 */
	public static String addParam(String url, String paramName,
			Object value) {
		if (url == null || paramName == null) {
			return url;
		}
		if (url.indexOf('?') == -1) {
			return url + '?' + paramName + '=' + value;
		}
		return url + '&' + paramName + '=' + value;
	}

	/**
	 * 
	 * @param url
	 * @param paramName
	 * @param value
	 * @return
	 */
	public static String replaceParamValue(String url, String paramName,
			Object value) {
		if (url == null || paramName == null) {
			return url;
		}
		String target = paramName + '=';
		int startPos = getParamStartPos(url, paramName);
		if (startPos == -1) {
			return url;
		}
		char ch = url.charAt(startPos - 1);
		if (ch != '?' && ch != '&') {
			return url;
		}

		int endPos = url.indexOf('&', startPos);
		if (endPos == -1) {
			String remain = url.substring(0, startPos - 1);
			return addParam(remain, paramName, value);
		}

		String partStart = url.substring(0, startPos);
		String partEnd = url.substring(endPos);
		return partStart + target + value + partEnd;
	}

	/**
	 * @param url
	 * @param paramName
	 * @return
	 */
	public static boolean paramExists(String url, String paramName) {
		return getParamStartPos(url, paramName) != -1;
	}

	static int getParamStartPos(String url, String paramName) {
		String target = paramName + '=';
		int startPos = url.indexOf(target);
		if (startPos < 1) {
			return -1;
		}
		char ch = url.charAt(startPos - 1);
		if (ch != '?' && ch != '&') {
			return -1;
		}

		return startPos;
	}

	/**
	 * 
	 * @param url
	 * @param paramName
	 * @param value
	 * @return
	 */
	public static String addOrReplaceParam(String url, String paramName,
			Object value) {
		if (paramExists(url, paramName)) {
			return replaceParamValue(url, paramName, value);
		}

		return addParam(url, paramName, value);
	}

	private UrlUtil() {
	}

	/**
	 * 对给定的字符串进行UTF-8方式的URL编码.
	 * 
	 * @see #encode(String, String)
	 */
	public static String encode(String str) {
		return encode(str, "UTF-8");
	}

	/**
	 * 对给定的字符串进行UTF-8方式的URL解码.
	 * 
	 * @see #decode(String, String)
	 */
	public static String decode(String str) {
		return decode(str, "UTF-8");
	}

	/**
	 * 对给定的字符串进行URL编码.
	 * 
	 * @param str
	 *            待编码的字符串
	 * @param encoding
	 *            编码方式
	 */
	public static String encode(String str, String encoding) {
		try {
			return URLEncoder.encode(str, encoding);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	/**
	 * 对给定的字符串进行URL解码.
	 * 
	 * @param str
	 *            待解码的字符串
	 * @param encoding
	 *            编码方式
	 */
	public static String decode(String str, String encoding) {
		try {
			return URLDecoder.decode(str, encoding);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	/**
	 * @param url
	 * @return
	 */
	public static boolean isFtp(URL url) {
		return "ftp".equals(url.getProtocol());
	}

	/**
	 * @param url
	 * @return
	 */
	public static String getHost(URL url) {
		String authority = url.getAuthority();
		int lastIndex = authority.lastIndexOf('@');
		if (lastIndex == -1) {
			return authority;
		}
		String hostAndPort = authority.substring(lastIndex + 1);
		int posOfColon = hostAndPort.indexOf(':');
		if (posOfColon == -1) {
			return hostAndPort;
		}
		return hostAndPort.substring(0, posOfColon);
	}

	/**
	 * @param url
	 * @return
	 */
	public static int getPort(URL url) {
		return (url.getPort() == -1) ? url.getDefaultPort() : url.getPort();
	}

	/**
	 * @param url
	 * @return
	 */
	public static String getRemoteDir(URL url) {
		String existedPath = url.getPath();
		if (false == StringHelper.isEmpty(existedPath)) {
			return existedPath;
		}
		String authority = url.getAuthority();
		String externalForm = url.toExternalForm();
		return StringHelper.substring(externalForm, authority,
				"?");
	}

	/**
	 * @param url
	 * @return
	 */
	public static String getUserName(URL url) {
		String userInfo = url.getUserInfo();
		if (userInfo == null) {
			return null;
		}
		final int index = userInfo.indexOf(':');
		if (index == -1) {
			return null;
		}
		return userInfo.substring(0, index);
	}

	/**
	 * @param url
	 * @return
	 */
	public static String getPassword(URL url) {
		String authority = url.getAuthority();
		int lastAt = authority.lastIndexOf('@');
		if (lastAt == -1) {
			return null;
		}
		int colon = authority.indexOf(':');
		if (colon == -1) {
			return null;
		}
		return authority.substring(colon + 1, lastAt);
	}

	/**
	 * 替换URL中的主机，支持HTTP/s、FTP等协议(即JDK支持的；比如RTP、RTSP等协议JDK默认就不支持)
	 * 
	 * @param strUrl
	 *            将被替换的URL
	 * @param host
	 *            要将主机替换成什么值
	 * @return 替换后的URL
	 */
	/*public static String replaceHostInUrl(String strUrl, String host) {
		URL url;
		try {
			url = new URL(strUrl);
		} catch (MalformedURLException e) {
			throw new WrappedException("MalformedURL: [" + strUrl + "]", e);
		}
		try {
			URL replaced = new URL(url.getProtocol(), host, url.getPort(), url.getFile());
			return replaced.toString();
		} catch (MalformedURLException e) {
			throw new WrappedException("MalformedURL because host: [" + host + "]", e);
		}
	}*/
}
