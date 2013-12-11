package com.sklay.core.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sklay.core.sdk.model.vo.SMSSetting;

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

	/**
	 * 信息资源
	 */
	private static Properties properties = new Properties();

	public static void instance(String mobile, String unicom, String telecom) {

		String propertyPath = get_WEB_INFO_PATH() + Constants.CONFIG_FILE_NAME;

		FileInputStream ios;
		try {
			ios = new FileInputStream(propertyPath);
			properties.load(ios);
			ios.close();

			if (StringUtils.isNotBlank(mobile))
				properties.setProperty(Constants.OPERATOR_MOBILE, mobile);

			if (StringUtils.isNotBlank(unicom))
				properties.setProperty(Constants.OPERATOR_UNICOM, unicom);

			if (StringUtils.isNotBlank(telecom))
				properties.setProperty(Constants.OPERATOR_TELECOM, telecom);

			FileOutputStream fos = new FileOutputStream(propertyPath);

			properties.store(fos, "Copyright (c) sklay.net");
			// 关闭流
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void instance(SMSSetting smsSetting) {

		LOG.info("PropertieUtils instance smsSetting is {}", smsSetting);

		if (null == smsSetting)
			return;

		String propertyPath = get_WEB_INFO_PATH() + Constants.CONFIG_FILE_NAME;

		FileInputStream ios;
		try {
			ios = new FileInputStream(propertyPath);
			properties.load(ios);
			ios.close();

			if (StringUtils.isNotBlank(smsSetting.getAccountId()))
				properties.setProperty(Constants.SMS_ACCOUNT, smsSetting
						.getAccountId().trim());

			if (StringUtils.isNotBlank(smsSetting.getPhysical()))
				properties.setProperty(Constants.SMS_PHYSICAL, smsSetting
						.getPhysical().trim());

			if (StringUtils.isNotBlank(smsSetting.getSos()))
				properties.setProperty(Constants.SMS_SOS, smsSetting.getSos()
						.trim());

			if (StringUtils.isNotBlank(smsSetting.getSendUrl()))
				properties.setProperty(Constants.SMS_URL, smsSetting
						.getSendUrl().trim());

			if (StringUtils.isNotBlank(smsSetting.getUserId()))
				properties.setProperty(Constants.SMS_USER_ID, smsSetting
						.getUserId().trim());

			if (StringUtils.isNotBlank(smsSetting.getPassword()))
				properties.setProperty(Constants.SMS_PASSWORD, smsSetting
						.getPassword().trim());

			FileOutputStream fos = new FileOutputStream(propertyPath);

			properties.store(fos, "Copyright (c) sklay.net");
			// 关闭流
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void instance(int verCode, String verName,
			String downloadUrl, String updateLog) {

		LOG.info(
				"PropertieUtils instance android version is verCode {} , verName{},downloadUrl {} ,updateLog {}",
				verCode, verName, downloadUrl, updateLog);

		if (StringUtils.isBlank(verName) || StringUtils.isBlank(downloadUrl)
				|| StringUtils.isBlank(updateLog) || verCode < 1)
			return;

		String propertyPath = get_WEB_INFO_PATH() + Constants.CONFIG_FILE_NAME;

		FileInputStream ios;
		try {
			ios = new FileInputStream(propertyPath);
			properties.load(ios);
			ios.close();

			if (StringUtils.isNotBlank(updateLog))
				properties.setProperty(Constants.ANDROID_UPDATE_LOG,
						updateLog.trim());

			if (StringUtils.isNotBlank(verName))
				properties.setProperty(Constants.ANDROID_VER_NAME,
						verName.trim());

			if (StringUtils.isNotBlank(downloadUrl))
				properties.setProperty(Constants.ANDROID_DOWLOAD_URL,
						downloadUrl.trim());

			if (verCode > 1)
				properties.setProperty(Constants.ANDROID_VER_CODE,
						String.valueOf(verCode));

			FileOutputStream fos = new FileOutputStream(propertyPath);

			properties.store(fos, "Copyright (c) sklay.net");
			// 关闭流
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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

	public static String get_WEB_INFO_PATH() {
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
