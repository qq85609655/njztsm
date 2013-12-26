package com.sklay.core.enums;

public enum SMSStatus implements LabeledEnum {

	/**
	 * 发送失败
	 */
	FAIL("发送失败", 0),

	/** 发送成功 */
	SUCCESS("发送成功", 1),
	
	/** 未发送 */
	NOSEND("未发送", 2);
	/** 成员变量 */
	private String lable;
	private int value;

	/** 构造方法 */
	private SMSStatus(String lable, int value) {
		this.lable = lable;
		this.value = value;
	}

	public static SMSStatus findByValue(int value) {
		switch (value) {
		case 0:
			return FAIL;
		case 1:
			return SUCCESS;
		case 2:
			return NOSEND;
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
