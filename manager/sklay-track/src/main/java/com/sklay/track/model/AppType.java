/*
 * Project:  any
 * Module:   any-track
 * File:     ClientType.java
 * Modifier: xyang
 * Modified: 2012-11-28 15:50
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.track.model;

import com.sklay.core.enums.LabeledEnum;

/**
 *  客户端程序类型
 *  .
 * <p/>
 *
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-8-9
 */
public enum AppType implements LabeledEnum {
	GENERIC("通用App", 0), BROWSER("网页App", 1), MOBILE("移动App", 2), PC("桌面App", 3), EMBEDDED(
			"嵌入式App", 4);
	private String label;
	private int value;

	AppType(String label, int value) {
		this.label = label;
		this.value = value;
	}

	@Override
	public String getLable() {
		return this.label;
	}

	@Override
	public int getValue() {
		return this.value;
	}
}
