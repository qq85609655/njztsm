package com.sklay.core.enums;

public enum MemberRole implements LabeledEnum {

	/**
	 * 普通用户
	 */
	MENBER("普通用户", 0),

	/**
	 * 代理商
	 */
	AGENT("代理商", 1),

	/**
	 * 系统管理员
	 */
	ADMINSTROTAR("系统管理员", 2);

	/**
	 * 成员变量
	 */
	private String lable;
	private int value;

	private MemberRole(String lable, int value) {
		this.lable = lable;
		this.value = value;
	}

	public static MemberRole findByValue(int value) {
		switch (value) {
		case 0:
			return MENBER;
		case 1:
			return AGENT;
		case 2:
			return ADMINSTROTAR;
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
