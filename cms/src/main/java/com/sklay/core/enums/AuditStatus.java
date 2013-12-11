package com.sklay.core.enums;

public enum AuditStatus implements LabeledEnum {
	/**
	 * 待审核
	 */
	WAIT("待审核", 0),

	/**
	 * 通过审核
	 */
	PASS("通过审核", 1),

	/**
	 * 未通过审核
	 */
	NOT("未通过审核", 2);
	
	/**
	 * 成员变量
	 */
	private String lable;
	private int value;

	/** 构造方法 */
	private AuditStatus(String lable, int value) {
		this.lable = lable;
		this.value = value;
	}

	public static AuditStatus findByValue(int value) {
		switch (value) {
		case 0:
			return WAIT;
		case 1:
			return PASS;
		case 2:
			return NOT;
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
