package com.sklay.core.sdk.model.vo;

import java.io.Serializable;

public class AddressComponent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2583198191464165180L;

	/**
	 * 城市名称
	 */
	private String city;

	/**
	 * 区县名称
	 */
	private String district;

	/**
	 * 省份名称
	 */
	private String province;

	/**
	 * 街道名称
	 */
	private String street;

	/**
	 * 门牌号码
	 */
	private String streetNumber;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

}
