package com.sklay.core.enums;

public enum UserAttrKey {

	/**
	 * 血压低值
	 */
	PRESSURE_LOW("血压低值", "lowPressure"),

	/**
	 * 血压高值
	 */
	PRESSURE_HIGH("血压高值", "highPressure"),
	/**
	 * 脉搏
	 */
	PULSE("脉搏", "pulse"),
	/**
	 * 经度
	 */
	LONGITUDE("经度", "longitude"),
	/**
	 * 纬度
	 */
	LATITUGE("纬度", "latitude");

	/** 成员变量 */
	private String lable;
	private String value;

	/** 构造方法 */
	private UserAttrKey(String lable, String value) {
		this.lable = lable;
		this.value = value;
	}

	/** 接口方法 */
	public String getLable() {
		return this.lable;
	}

	public String getValue() {
		return this.value;
	}

	public static UserAttrKey findByValue(String value) {

		if ("lowPressure".equals(value))
			return PRESSURE_LOW;

		if ("highPressure".equals(value))
			return PRESSURE_HIGH;

		if ("pulse".equals(value))
			return PULSE;

		if ("longitude".equals(value))
			return LONGITUDE;

		if ("latitude".equals(value))
			return LATITUGE;

		return null;
	}

	public static UserAttrKey findByValue(int value) {
		switch (value) {
		case 0:
			return PRESSURE_LOW;
		case 1:
			return PRESSURE_HIGH;
		case 2:
			return PULSE;
		case 3:
			return LONGITUDE;
		case 4:
			return LATITUGE;
		default:
			return null;
		}
	}
}
