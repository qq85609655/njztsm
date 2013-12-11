package com.sklay.core.enums;

public enum MoldType implements LabeledEnum {

	/**
	 * 短信推广业务
	 */
	SMS("短信推广业务", 0),

	/**
	 * 设备付费业务
	 */
	DEVICE("设备付费业务", 1);

	/**
	 * 成员变量
	 */
	private String lable;
	private int value;

	private MoldType(String lable, int value) {
		this.lable = lable;
		this.value = value;
	}

	public static MoldType findByValue(int value) {
		switch (value) {
		case 0:
			return SMS;
		case 1:
			return DEVICE;
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
