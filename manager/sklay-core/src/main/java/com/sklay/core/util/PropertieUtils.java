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

			if (StringUtils.isNotBlank(smsSetting.getSendUrl()))
				properties.setProperty(Constants.SMS_URL, smsSetting
						.getSendUrl().trim());

			if (StringUtils.isNotBlank(smsSetting.getChangePwd()))
				properties.setProperty(Constants.SMS_CHANGE_PWD, smsSetting
						.getChangePwd().trim());

			if (StringUtils.isNotBlank(smsSetting.getBalance()))
				properties.setProperty(Constants.SMS_BALANCE, smsSetting
						.getBalance().trim());

			if (StringUtils.isNotBlank(smsSetting.getPassword()))
				properties.setProperty(Constants.SMS_PASSWORD, smsSetting
						.getPassword().trim());

			if (StringUtils.isNotBlank(smsSetting.getAccount()))
				properties.setProperty(Constants.SMS_ACCOUNT, smsSetting
						.getAccount().trim());

			if (StringUtils.isNotBlank(smsSetting.getPhysicalTpl()))
				properties.setProperty(Constants.SMS_TEMPLATE_PHYSICAL,
						smsSetting.getPhysicalTpl().trim());

			if (StringUtils.isNotBlank(smsSetting.getSosTpl()))
				properties.setProperty(Constants.SMS_TEMPLATE_SOS, smsSetting
						.getSosTpl().trim());

			if (StringUtils.isNotBlank(smsSetting.getSosPairs()))
				properties.setProperty(Constants.SMS_SOS_PAIRS, smsSetting
						.getSosPairs().trim());

			if (StringUtils.isNotBlank(smsSetting.getPwdPairs()))
				properties.setProperty(Constants.SMS_PWD_PAIRS, smsSetting
						.getPwdPairs().trim());

			if (StringUtils.isNotBlank(smsSetting.getSignTpl()))
				properties.setProperty(Constants.SMS_TEMPLATE_SIGN, smsSetting
						.getSignTpl().trim());

			if (StringUtils.isNotBlank(smsSetting.getBirthdayTpl()))
				properties.setProperty(Constants.SMS_TEMPLATE_BIRTHDAY,
						smsSetting.getBirthdayTpl().trim());

			if (StringUtils.isNotBlank(smsSetting.getBirthPairs()))
				properties.setProperty(Constants.SMS_BIRTHDAY_PAIRS, smsSetting
						.getBirthPairs().trim());

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

		LOG.debug(
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
