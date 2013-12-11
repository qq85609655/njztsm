package com.sklay.core.enums;

public enum SMSType implements LabeledEnum {
	/**
	 * 体检
	 */
	PHYSICAL("体检", 0),

	/**
	 * 定位
	 */
	LOCATION("定位", 1),

	/**
	 * 定制短信
	 */
	BESPOKE("定制短信", 2)

	;
	/** 成员变量 */
	private String lable;
	private int value;

	/** 构造方法 */
	private SMSType(String lable, int value) {
		this.lable = lable;
		this.value = value;
	}

	public static SMSType findByValue(int value) {
		switch (value) {
		case 0:
			return PHYSICAL;
		case 1:
			return LOCATION;
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
