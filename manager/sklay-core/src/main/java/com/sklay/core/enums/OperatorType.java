package com.sklay.core.enums;

/**
 * 
 * .
 * <p/>
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-7-3
 */
public enum OperatorType implements LabeledEnum {
	/**
	 * 未知
	 */
	UNKNOWN("未知", 0),

	/** 中国移动 */
	CHINAMOBILE("中国移动", 1),

	/** 中国电信 */
	CHINATELECOM("中国电信", 2),

	/** 中国联通 */
	CHINAUNICOM("中国联通", 3),

	/** 手机号错误 */
	PHONEERROE("手机号错误", 4);

	/** 成员变量 */
	private String lable;
	private int value;

	/** 构造方法 */
	private OperatorType(String lable, int value) {
		this.lable = lable;
		this.value = value;
	}

	public static OperatorType findByValue(int value) {
		switch (value) {
		case 0:
			return UNKNOWN;
		case 1:
			return CHINAMOBILE;
		case 2:
			return CHINAUNICOM;
		case 3:
			return CHINATELECOM;
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
