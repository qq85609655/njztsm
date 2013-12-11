package com.sklay.core.enums;

public enum SwitchStatus implements LabeledEnum {
	/**
	 * 关闭
	 */
	CLOSE("关闭", 0),

	/**
	 * 开启
	 */
	OPEN("开启", 1) ,

	/**
	 * 时间段
	 */
	PERIOD("时间段", 2)
	;
	private final String lable;
	private final int value;

	private SwitchStatus(String lable, int value) {
		this.lable = lable;
		this.value = value;
	}

	public static SwitchStatus findByValue(int value) {
		switch (value) {
		case 0:
			return CLOSE;
		case 1:
			return OPEN;
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
