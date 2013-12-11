package com.sklay.core.sdk.model.vo;

import java.io.Serializable;

public class SMSSetting implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8070494530677083776L;

	private String userId;

	private String accountId;

	private String password;

	private String sendUrl;

	private String sos;

	private String physical;

	private String mobile;

	private String unicom;

	private String telecom;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSendUrl() {
		return sendUrl;
	}

	public void setSendUrl(String sendUrl) {
		this.sendUrl = sendUrl;
	}

	public SMSSetting() {
		super();
	}

	public String getSos() {
		return sos;
	}

	public void setSos(String sos) {
		this.sos = sos;
	}

	public String getPhysical() {
		return physical;
	}

	public void setPhysical(String physical) {
		this.physical = physical;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUnicom() {
		return unicom;
	}

	public void setUnicom(String unicom) {
		this.unicom = unicom;
	}

	public String getTelecom() {
		return telecom;
	}

	public void setTelecom(String telecom) {
		this.telecom = telecom;
	}

	public SMSSetting(String userId, String accountId, String password,
			String sendUrl, String physical, String sos, String mobile,
			String unicom, String telecom) {
		super();
		this.userId = userId;
		this.accountId = accountId;
		this.password = password;
		this.sendUrl = sendUrl;
		this.physical = physical;
		this.sos = sos;
		this.mobile = mobile;
		this.unicom = unicom;
		this.telecom = telecom;

	}

	public SMSSetting(String mobile, String unicom, String telecom) {
		super();
		this.mobile = mobile;
		this.unicom = unicom;
		this.telecom = telecom;
	}

}
