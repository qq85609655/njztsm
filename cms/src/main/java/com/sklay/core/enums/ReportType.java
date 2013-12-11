package com.sklay.core.enums;

public enum ReportType implements LabeledEnum {

	/**
	 * 体检
	 */
	PHYSICAL("体检", 0),

	/**
	 * 定位求助
	 */
	SOS("定位求助", 1);

	/**
	 * 成员变量
	 */
	private String lable;
	private int value;

	private ReportType(String lable, int value) {
		this.lable = lable;
		this.value = value;
	}

	public static ReportType findByValue(int value) {
		switch (value) {
		case 0:
			return PHYSICAL;
		case 1:
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
