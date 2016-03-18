package com.xu.util;

public class StringUtil {
	/**
	 * 将Unicode转换成汉字
	 * 
	 * @param utfString
	 * @return
	 */
	public static String convertUnicode(String utfString) {
		StringBuilder sb = new StringBuilder();
		int i = -1;
		int pos = 0;

		while ((i = utfString.indexOf("\\u", pos)) != -1) {
			sb.append(utfString.substring(pos, i));
			if (i + 5 < utfString.length()) {
				pos = i + 6;
				sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
			}
		}

		return sb.toString();
	}

	/**
	 * 正则
	 */
	public static String replaceBlank(String str) {
		if (str != null) {
			return str.replaceAll("[\b\r\n\t]*", "");
		}
		return null;
	}
}
