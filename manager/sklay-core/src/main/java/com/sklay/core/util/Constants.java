package com.sklay.core.util;

import java.io.File;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

/**
 * 
 * .
 * <p/>
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-7-29
 */
public class Constants {
	public static final String UTF8 = "UTF-8";
	public static final String DEFAULT_CHARSET = UTF8;
	public static final Charset CHARSET = Charset.forName(DEFAULT_CHARSET);
	public static final int ZERO = 0;

	/**
	 * shrio授权缓存key
	 */
	public static final String SHIRO_AUTHOR_ACAHE = "shiroAuthorizationCache";

	public static final int ALL = -1;

	public static final String NODE_AGENT = "node_agent_";
	public static final String NODE_MEMBER = "node_member_";
	/**
	 * 移动或联通
	 */
	public static final String MOBILE2NUICOM = "1";

	/**
	 * 电信
	 */
	public static final String TELECOM = "2";
	public static final String RET = "ret";

	public static final Pattern EMAIL_PATTERN = Pattern
			.compile("^([a-z0-9A-Z]+[-|\\\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\\\.)+[a-zA-Z]{2,}$");
	public static final Pattern MOBILE_PATTERN = Pattern
			.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");

	public static final char SEPARATOR = '^';
	public static final char REDIS_SEPARATOR = ':';

	public static final String CONFIG_FILE_NAME = "/config.properties";

	public static final String SMS_URL = "sms.url";

	public static final String SMS_ACCOUNT = "sms.account";

	public static final String SMS_PASSWORD = "sms.password";

	public static final String SMS_CHANGE_PWD = "sms.changepwd";

	public static final String SMS_BALANCE = "sms.balance";

	public static final String SMS_TEMPLATE_PHYSICAL = "sms.physical.template";

	public static final String SMS_TEMPLATE_SOS = "sms.sos.template";
	
	public static final String SMS_TEMPLATE_BIRTHDAY = "sms.birthday.template";

	public static final String SMS_SOS_PAIRS = "sms.sos.pairs";

	public static final String SMS_JOB = "sms.cronExpression";

	public static final String SMS_TEMPLATE_PWD = "sms.pwd.template";

	public static final String SMS_PWD_PAIRS = "sms.pwd.pairs";
	
	public static final String SMS_BIRTHDAY_PAIRS = "sms.birthday.pairs";

	public static final String SMS_TEMPLATE_SIGN = "sms.sign.template";

	public static final String ANDROID_VER_CODE = "client.android.ver.code";

	public static final String ANDROID_VER_NAME = "client.android.ver.name";

	public static final String ANDROID_DOWLOAD_URL = "client.android.download.url";

	public static final String ANDROID_UPDATE_LOG = "client.android.update.log";

	public static final String APP_PUBLISH_PATH = "WEB-INF" + File.separator
			+ "version";

	public static final String ANY_HOME = "any.home";
	public static final String ANY_PROFILE_ACTIVE = "any.profile.active";
	public static final String FILE_LOG = "filelog.enable";
	public static final String PROFILE_DEV = "dev";
	public static final String PROFILE_TEST = "test";
	public static final String PROFILE_PRODUCTION = "production";

	/** 付费用户使用短信业务Key */
	public static final String DEVICE_MOLD_TIME = "moldTime";

	public static class EventTopic {
		public static final String COMMENT_SAVE = "comment_save";
	}
}
