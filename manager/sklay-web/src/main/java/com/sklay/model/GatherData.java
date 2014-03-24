package com.sklay.model;

import java.io.Serializable;

import com.sklay.core.sdk.model.vo.Coordinates;

public class GatherData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6609185399269613192L;

	private String simNo = "";

	/** 血压低值 */
	private String lowPressure = "";

	/** 血压高值 */
	private String highPressure = "";

	/** 脉搏 */
	private String pulse = "";

	/** 经度 */
	private String longitude = "";

	/** 纬度 */
	private String latitude = "";

	private int indexHealth = 0;

	/**
	 * 转化后坐标
	 */
	private Coordinates coordinates;

	public String getSimNo() {
		return simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	public String getLowPressure() {
		return lowPressure;
	}

	public void setLowPressure(String lowPressure) {
		this.lowPressure = lowPressure;
	}

	public String getHighPressure() {
		return highPressure;
	}

	public void setHighPressure(String highPressure) {
		this.highPressure = highPressure;
	}

	public String getPulse() {
		return pulse;
	}

	public void setPulse(String pulse) {
		this.pulse = pulse;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "GatherData [simNo=" + simNo + ", lowPressure=" + lowPressure
				+ ", highPressure=" + highPressure + ", pulse=" + pulse
				+ ", longitude=" + longitude + ", latitude=" + latitude + "]";
	}

	public int getIndexHealth() {
		return indexHealth;
	}

	public void setIndexHealth(int indexHealth) {
		this.indexHealth = indexHealth;
	}

	public String getSimno() {
		return simNo;
	}

	public void setSimno(String simno) {
		this.simNo = simno;
	}

	public String getLowpressure() {
		return lowPressure;
	}

	public void setLowpressure(String lowpressure) {
		this.lowPressure = lowpressure;
	}

	public String getHighpressure() {
		return highPressure;
	}

	public void setHighpressure(String highpressure) {
		this.highPressure = highpressure;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

}
