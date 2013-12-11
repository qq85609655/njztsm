package com.sklay.core.sdk.model.vo;

import java.io.Serializable;

public class LocationDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3653099334831232297L;

	/**
	 * 坐标
	 */
	private Coordinates location;

	/**
	 * 详细地址描述
	 */
	private String formatted_address;

	/**
	 * 城市代码
	 */
	private String cityCode;

	/**
	 * 周围商圈
	 */
	private String business;

	/**
	 * 地址组成信息
	 */
	private AddressComponent addressComponent;

	public Coordinates getLocation() {
		return location;
	}

	public void setLocation(Coordinates location) {
		this.location = location;
	}

	public String getFormatted_address() {
		return formatted_address;
	}

	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public AddressComponent getAddressComponent() {
		return addressComponent;
	}

	public void setAddressComponent(AddressComponent addressComponent) {
		this.addressComponent = addressComponent;
	}

}
