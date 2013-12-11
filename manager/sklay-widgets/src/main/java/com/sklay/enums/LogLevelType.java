package com.sklay.enums;

import com.sklay.core.enums.LabeledEnum;

/**
 * 
 * .
 * <p/>
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-7-3
 */
public enum LogLevelType implements LabeledEnum {
	/**
	 * API调用日志
	 */
	API("API调用日志", 0),

	/** 用户service服务日志 */
	SERVICE("用户service服务日志", 1),

	/** 后台ADMIN服务日志 */
	ADMIN("后台ADMIN服务日志", 2),

	/** 设备的付费业务 */
	DEVICE_MOLD("设备的付费业务", 3);

	/** 成员变量 */
	private String lable;
	private int value;

	/** 构造方法 */
	private LogLevelType(String lable, int value) {
		this.lable = lable;
		this.value = value;
	}

	public static LogLevelType findByValue(int value) {
		switch (value) {
		case 0:
			return API;
		case 1:
			return SERVICE;
		case 2:
			return ADMIN;
		case 3:
			return DEVICE_MOLD;
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
