package com.sklay.core.enums;

import com.sklay.core.enums.LabeledEnum;


/**
 * 数据获取策略
 */
public enum FetchType implements LabeledEnum {
	/**
	 * 延迟加载（不获取关联数据）
	 */
	LAZY("延迟加载", 0),
	/**
	 * 完全加载（获取关联数据）
	 */
	EAGER("完全加载", 1);

	private final String lable;
	private final int value;

	private FetchType(String lable, int value) {
		this.lable = lable;
		this.value = value;
	}
	
	public static FetchType findByValue(int value) {
		switch (value) {
		case 0:
			return LAZY;
		case 1:
			return EAGER;
		default:
			return null;
		}
	}
	
	@Override
	public int getValue() {
		return this.value;
	}

	@Override
	public String getLable() {
		return this.lable;
	}
}
