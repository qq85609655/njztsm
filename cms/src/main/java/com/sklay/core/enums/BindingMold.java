package com.sklay.core.enums;

public enum BindingMold implements LabeledEnum {
	/**
	 * 免费
	 */
	FREE("免费", 0),

	/**
	 * 付费
	 */
	PAID("付费", 1);

	/**
	 * 成员变量
	 */
	private String lable;
	private int value;

	/** 构造方法 */
	private BindingMold(String lable, int value) {
		this.lable = lable;
		this.value = value;
	}

	public static BindingMold findByValue(int value) {
		switch (value) {
		case 0:
			return FREE;
		case 1:
			return PAID;
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
