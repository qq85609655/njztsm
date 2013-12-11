package com.sklay.core.enums;

public enum DelStatus implements LabeledEnum {

	/**
	 * 保存
	 */
	SAVE("保存", 0),

	/**
	 * 已刪除
	 */
	DELETE("已刪除", 1);

	/**
	 * 成员变量
	 */
	private String lable;
	private int value;

	/** 构造方法 */
	private DelStatus(String lable, int value) {
		this.lable = lable;
		this.value = value;
	}

	public static DelStatus findByValue(int value) {
		switch (value) {
		case 0:
			return SAVE;
		case 1:
			return DELETE;
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
