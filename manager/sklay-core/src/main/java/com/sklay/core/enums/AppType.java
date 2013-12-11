package com.sklay.core.enums;

public enum AppType implements LabeledEnum {

	/**
	 * 短信推广业务
	 */
	SMS("短信推广业务", 0);

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
			return SMS;
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