package com.sklay.core.sdk.model.vo;

import java.io.Serializable;

public class Coordinates implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6335289646276102862L;

	/**
	 * 经度
	 */
	private String x;

	/**
	 * 纬度
	 */
	private String y;

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Coordinates [x=" + x + ", y=" + y + "]";
	}

}
