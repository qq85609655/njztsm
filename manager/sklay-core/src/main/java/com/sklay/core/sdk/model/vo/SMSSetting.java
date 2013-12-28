package com.sklay.core.sdk.model.vo;

import java.io.Serializable;

public class SMSSetting implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8070494530677083776L;

	private String account;

	private String password;

	private String sendUrl;

	private String sos;

	private String physical;

	private String pwd;

	private String sign;

	private String sosPairs;

	private String pwdPairs;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public SMSSetting(String pwd, String account, String password,
			String sendUrl, String physical, String sos, String sign,
			String sosPairs, String pwdPairs) {
		super();
		this.sosPairs = sosPairs;
		this.pwdPairs = pwdPairs;
		this.sign = sign;
		this.pwd = pwd;
		this.account = account;
		this.password = password;
		this.sendUrl = sendUrl;
		this.physical = physical;
		this.sos = sos;

	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSosPairs() {
		return sosPairs;
	}

	public void setSosPairs(String sosPairs) {
		this.sosPairs = sosPairs;
	}

	public String getPwdPairs() {
		return pwdPairs;
	}

	public void setPwdPairs(String pwdPairs) {
		this.pwdPairs = pwdPairs;
	}
}
