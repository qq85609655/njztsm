package com.sklay.core.enums;

public enum WidgetLevel implements LabeledEnum {
	
	/**
	 * 一级
	 */
	FIRST("一级", 0),

	/**
	 * 二级
	 */
	SECOND("二级", 1),

	/**
	 * 三级
	 */
	THIRD("三级", 2);

	/** 成员变量 */
	private String lable;
	private int value;

	/** 构造方法 */
	private WidgetLevel(String lable, int value) {
		this.lable = lable;
		this.value = value;
	}

	/** 接口方法 */
	@Override
	public String getLable() {
		return this.lable;
	}

	@Override
	public int getValue() {
		return this.value;
	}

	public static WidgetLevel findByValue(int value) {
		switch (value) {
		case 0:
			return FIRST;
		case 1:
			return SECOND;
		case 2:
			return THIRD;
		default:
			return null;
		}
	}

}
