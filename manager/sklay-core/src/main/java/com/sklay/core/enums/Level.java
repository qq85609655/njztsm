package com.sklay.core.enums;

import com.sklay.core.enums.LabeledEnum;

public enum Level implements LabeledEnum {
	/**
	 * 主账号
	 */
	FIRST("主账号", 0),

	/**
	 * 附属账号
	 */
	SECOND("附属账号", 1)

	;
	private final String lable;
	private final int value;

	private Level(String lable, int value) {
		this.lable = lable;
		this.value = value;
	}

	public static Level findByValue(int value) {
		switch (value) {
		case 0:
			return FIRST;
		case 1:
			return SECOND;
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
