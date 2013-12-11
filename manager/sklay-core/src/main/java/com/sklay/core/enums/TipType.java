package com.sklay.core.enums;

public enum TipType implements LabeledEnum {
	/**
	 * 信息
	 */
	INFO("信息", 0),

	/**
	 * 错误
	 */
	ERROR("错误", 1),

	/**
	 * 警告
	 */
	WARNING("警告", 2)

	;
	/** 成员变量 */
	private String lable;
	private int value;

	/** 构造方法 */
	private TipType(String lable, int value) {
		this.lable = lable;
		this.value = value;
	}

	public static TipType findByValue(int value) {
		switch (value) {
		case 0:
			return INFO;
		case 1:
			return ERROR;
		case 2:
			return WARNING;
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
