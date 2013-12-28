package com.sklay.model;

public class SMSView extends SMS {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String reciverPhone;

	private String reciverUser;

	private String physical;

	public String getReciverPhone() {
		return reciverPhone;
	}

	public void setReciverPhone(String reciverPhone) {
		this.reciverPhone = reciverPhone;
	}

	public String getReciverUser() {
		return reciverUser;
	}

	public void setReciverUser(String reciverUser) {
		this.reciverUser = reciverUser;
	}

	public String getPhysical() {
		return physical;
	}

	public void setPhysical(String physical) {
		this.physical = physical;
	}
}
