package com.sklay.core.enums;

public enum ReadType implements LabeledEnum {

	/**
	 * 待阅读
	 */
	WAIT("待阅读", 0),

	/**
	 * 已阅读
	 */
	READED("已阅读", 1)

	;
	/** 成员变量 */
	private String lable;
	private int value;

	/** 构造方法 */
	private ReadType(String lable, int value) {
		this.lable = lable;
		this.value = value;
	}

	public static ReadType findByValue(int value) {
		switch (value) {
		case 0:
			return WAIT;
		case 1:
			return READED;
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
