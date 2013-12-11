package com.sklay.core.util;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * .
 * <p/>
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-7-22
 */
public class PropertieUtils {

	private static final Logger LOG = LoggerFactory
			.getLogger(PropertieUtils.class.getName());

	public static String getRootPath() {
		String path = null;
		try {
			path = PropertieUtils.class.getResource("/").toURI().getPath();
			path = path.substring(1, path.length() - 1);
			String str = "WEB-INF/classes";
			int i = path.indexOf(str);
			if (i > 0) {
				path = path.substring(0, i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	public static String get_WEB_INFO_Path() {
		String path = null;
		try {
			path = PropertieUtils.class.getResource("/").toURI().getPath();
			path = path.substring(1, path.length() - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}
}
