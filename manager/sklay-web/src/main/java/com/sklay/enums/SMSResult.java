package com.sklay.enums;

import com.sklay.core.enums.LabeledEnum;

/**
 * 
 * .
 * <p/>
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-7-3
 */
public enum SMSResult implements LabeledEnum {

	/**
	 * 修改成功
	 */
	SUCCESS("修改成功", 0),
	
	/**
	 * 查询余额失败
	 */
	FAILD_BALANCE("查询余额失败", 100),

	/**
	 * 用户账号不存在或密码错误
	 */
	ERROR_COUNNT_PWD("用户账号不存在或密码错误", 101),

	/**
	 * 账号已禁用
	 */
	DISABLE_COUNT("账号已禁用", 102),

	/**
	 * 参数不正确
	 */
	ERROR_PARAM("参数不正确", 103),

	/**
	 * 提交过于频繁,超过1分钟内限定的流量
	 */
	ERROE_OFTEN("提交过于频繁,超过1分钟内限定的流量(用户访问时间间隔低于50毫秒)", 104),

	/**
	 * 短信内容超过300字、或为空、或内容编码格式不正确
	 */
	ERROE_CONTENT("短信内容超过300字、或为空、或内容编码格式不正确", 105),

	/**
	 * 手机号码超过100个或合法的手机号码为空
	 */
	ERROE_PHONE("手机号码超过100个或合法的手机号码为空", 106),

	/**
	 * 用户账户不支持使用扩展码
	 */
	ERROE_COUNNT_SUPPORT("用户账户不支持使用扩展码", 107),

	/**
	 * 余额不足
	 */
	ERROE_MONEY("余额不足", 108),

	/**
	 * 指定访问ip地址错误
	 */
	ERROE_IP("指定访问ip地址错误", 109),

	/**
	 * 短信内容存在系统保留关键词，如有多个词，使用逗号分隔：110#(110#( 李 老师 ,XX,XX,XX,成人 )
	 */
	ERROE_WORD("短信内容存在系统保留关键词，如有多个词，使用逗号分隔：110#(110#( 李 老师 ,XX,XX,XX,成人 )", 110),

	/**
	 * 超出当天限发条数超出
	 */
	ERROE_DAY_LIMIT("超出当天限发条数超出", 111),

	/**
	 * 预约发送时间格式错误
	 */
	ERROE_TIME_FORMAT("预约发送时间格式错误", 112),

	/**
	 * 短信签名为空或者格式错误
	 */
	ERROE_SIGN("短信签名为空或者格式错误", 113),

	/**
	 * 模板短信序号不存在
	 */
	ERROE_TEMPLATE("模板短信序号不存在", 114),

	/**
	 * 短信签名标签序号不存在
	 */
	ERROE_SIGN_NO("短信签名标签序号不存在", 115);

	/** 成员变量 */
	private String lable;
	private int value;

	/** 构造方法 */
	private SMSResult(String lable, int value) {
		this.lable = lable;
		this.value = value;
	}

	public static SMSResult findByValue(int value) {
		switch (value) {
		case 0:
			return SUCCESS;
		case 100:
			return FAILD_BALANCE;
		case 101:
			return ERROR_COUNNT_PWD;
		case 102:
			return DISABLE_COUNT;
		case 103:
			return ERROR_PARAM;
		case 104:
			return ERROE_OFTEN;
		case 105:
			return ERROE_CONTENT;
		case 106:
			return ERROE_PHONE;
		case 107:
			return ERROE_COUNNT_SUPPORT;
		case 108:
			return ERROE_MONEY;
		case 109:
			return ERROE_IP;
		case 110:
			return ERROE_WORD;
		case 111:
			return ERROE_DAY_LIMIT;
		case 112:
			return ERROE_TIME_FORMAT;
		case 113:
			return ERROE_SIGN;
		case 114:
			return ERROE_TEMPLATE;
		case 115:
			return ERROE_SIGN_NO;
		default:
			return null;
		}
	}

	@Override
	public String getLable() {
		return this.lable;
	}

	@Override
	public int getValue() {
		return this.value;
	}

}
