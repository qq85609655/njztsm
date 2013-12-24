package com.sklay.model;

import java.io.Serializable;

public class ChartData implements Serializable {

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

	public int getIndexHealth() {
		return indexHealth;
	}

	public void setIndexHealth(int indexHealth) {
		this.indexHealth = indexHealth;
	}

	public ChartData() {
		super();
	}

	public ChartData(String simNo, String lowPressure, String highPressure,
			String pulse, String longitude, String latitude, int indexHealth) {
		super();
		this.simNo = simNo;
		this.lowPressure = lowPressure;
		this.highPressure = highPressure;
		this.pulse = pulse;
		this.longitude = longitude;
		this.latitude = latitude;
		this.indexHealth = indexHealth;
	}

	public ChartData(String simNo, String lowPressure, String highPressure) {
		super();
		this.simNo = simNo;
		this.lowPressure = lowPressure;
		this.highPressure = highPressure;
	}

	@Override
	public String toString() {
		return "ChartData [simNo=" + simNo + ", lowPressure=" + lowPressure
				+ ", highPressure=" + highPressure + ", pulse=" + pulse
				+ ", longitude=" + longitude + ", latitude=" + latitude
				+ ", indexHealth=" + indexHealth + "]";
	}
}
