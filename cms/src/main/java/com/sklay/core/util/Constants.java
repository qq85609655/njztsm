package com.sklay.core.util;

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
			.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

	public static final char SEPARATOR = '^';
	public static final char REDIS_SEPARATOR = ':';

	public static final String CONFIG_FILE_NAME = "/config.properties";

	public static final String SMS_URL = "sms.url";

	public static final String SMS_ACCOUNT = "sms.account";

	public static final String SMS_USER_ID = "sms.userId";

	public static final String SMS_PASSWORD = "sms.password";

	public static final String SMS_PHYSICAL = "sms.physical.template";

	public static final String SMS_SOS = "sms.sos.template";

	public static final String SMS_JOB = "sms.cronExpression";

	public static final String ANY_HOME = "any.home";
	public static final String ANY_PROFILE_ACTIVE = "any.profile.active";
	public static final String FILE_LOG = "filelog.enable";
	public static final String PROFILE_DEV = "dev";
	public static final String PROFILE_TEST = "test";
	public static final String PROFILE_PRODUCTION = "production";

	public static class EventTopic {
		public static final String COMMENT_SAVE = "comment_save";
	}
}
