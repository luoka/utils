/**
 * Title:		luoka
 */
package com.luoka.commons;

/**
 * 便于获取OS及JVM的一些值.<br>
 * @since zengqingmeng @ Apr 3, 2014 9:25:57 PM
 *
 */
public class EnvConst {
	/**
	 * Linux下环境变量LIBRARY_PATH的名称.
	 */
	public static final String LIBRARY_PATH_LINUX = "LD_LIBRARY_PATH";

	/**
	 * 环境变量PATH的名称.
	 */
	public static final String PATH = "PATH";

	/**
	 * 操作系统名称.
	 */
	public static final String OS_NAME = System.getProperty("os.name");

	/**
	 * 换行符.
	 */
	public static final String NEWLINE = System.getProperty("line.separator");

	/**
	 * 操作系统的硬件架构.
	 */
	public static final String OS_ARCH = System.getProperty("os.arch");

	/**
	 * JVM默认编码.
	 */
	public static final String ENCODING = System.getProperty("file.encoding");
	
	public static final String OS_FAMILY_UNKNOWN = "unknown";
	
	public static final String OS_FAMILY_LINUX = "linux";
	
	public static final String OS_FAMILY_MAC = "mac";
	
	public static final String OS_FAMILY_WINDOWS = "windows";

	/**
	 * 当前操作系统是否是Windows
	 * 
	 * @return
	 */
	public static boolean isWindows() {
		return getOSNameInUpperCase().indexOf("WINDOWS") >= 0;
	}

	/**
	 * 当前操作系统是否是Linux
	 * 
	 * @return
	 */
	public static boolean isLinux() {
		return getOSNameInUpperCase().indexOf("LINUX") >= 0;
	}

	/**
	 * 当前操作系统是否是Mac
	 * 
	 * @return
	 */
	public static boolean isMac() {
		return getOSNameInUpperCase().indexOf("MAC") >= 0;
	}

	/**
	 * 得到当前操作系统名称，大写
	 * 
	 * @return
	 */
	public static String getOSNameInUpperCase() {
		if (OS_NAME == null) {
			return "UNKNOWN!";
		}
		return OS_NAME.toUpperCase();
	}

	/**
	 * 返回操作系统家族的名称(均为小写).
	 */
	public static String getOSFamilyName() {
		if (isWindows()) {
			return OS_FAMILY_WINDOWS;
		}
		if (isLinux()) {
			return OS_FAMILY_LINUX;
		}
		if (isMac()) {
			return OS_FAMILY_MAC;
		}
		return OS_FAMILY_UNKNOWN;
	}

	/**
	 * 获取执行该JVM进程的用户。
	 * 
	 * @return 执行该JVM进程的用户
	 */
	public static String getProcessUser() {
		return System.getProperty("user.name");
	}
}
