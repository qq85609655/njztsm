package com.sklay.util;

import java.util.regex.Matcher;

import org.apache.commons.lang.StringUtils;

import com.sklay.core.util.Constants;

/**
 * 获取手机运营商 .
 * <p/>
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-7-3
 */
public class MobileUtil {

	public static boolean isMobile(String mobiles) {

		if (StringUtils.isBlank(mobiles))
			return false;

		Matcher m = Constants.MOBILE_PATTERN.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 计算字符串的长度. 一个字符长度为1，一个汉字的长度为2.
	 * 
	 * @param String
	 * @return int
	 */
	public static int lengthOfQuanJiao(String value) {
		if (value == null)
			return 0;
		StringBuffer buff = new StringBuffer(value);
		int length = 0;
		String stmp;
		for (int i = 0; i < buff.length(); i++) {
			stmp = buff.substring(i, i + 1);
			try {
				stmp = new String(stmp.getBytes("utf-8"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (stmp.getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length;
	}

	public static void main(String[] args) {

		System.out.println(60/70);
		
//		String s = "你好啊dd, ";
//		System.out.println("length1: " + s.length());
//		System.out.println("length2: " + s.getBytes().length);
//
//		char[] chars = s.toCharArray();
//		int m = chars.length;
//		byte b[] = s.getBytes(Charset.forName("SHIFT_JIS"));
//		System.out.println("length3: " + lengthOfQuanJiao(s));
	}
}
