package com.sklay.core.enums;

public enum AppType implements LabeledEnum {

	/**
	 * 短信推广业务
	 */
	PUSH("短信推广业务", 0),

	/**
	 * 体检业务
	 */
	PHYSICAL("体检业务", 1),

	/**
	 * 求救业务
	 */
	SOS("求救业务", 2);

	/**
	 * 成员变量
	 */
	private String lable;
	private int value;

	private AppType(String lable, int value) {
		this.lable = lable;
		this.value = value;
	}

	public static AppType findByValue(int value) {
		switch (value) {
		case 0:
			return PUSH;
		case 1:
			return PHYSICAL;
		case 2:
			return SOS;
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