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

	private String sosTpl;

	private String physicalTpl;

	private String pwdTpl;

	private String signTpl;

	private String birthdayTpl;

	private String sosPairs;

	private String pwdPairs;

	private String birthPairs;

	private String changePwd;

	private String balance;

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

	public String getSosTpl() {
		return sosTpl;
	}

	public void setSosTpl(String sosTpl) {
		this.sosTpl = sosTpl;
	}

	public String getPhysicalTpl() {
		return physicalTpl;
	}

	public void setPhysicalTpl(String physicalTpl) {
		this.physicalTpl = physicalTpl;
	}

	public SMSSetting(String pwdTpl, String account, String password,
			String sendUrl, String physicalTpl, String sosTpl, String signTpl,
			String sosPairs, String pwdPairs, String changePwd, String balance,
			String birthdayTpl, String birthPairs) {
		super();
		this.sosPairs = sosPairs;
		this.pwdPairs = pwdPairs;
		this.signTpl = signTpl;
		this.pwdTpl = pwdTpl;
		this.account = account;
		this.password = password;
		this.sendUrl = sendUrl;
		this.physicalTpl = physicalTpl;
		this.sosTpl = sosTpl;
		this.changePwd = changePwd;
		this.balance = balance;
		this.birthdayTpl = birthdayTpl;
		this.birthPairs = birthPairs;

	}

	public String getPwdTpl() {
		return pwdTpl;
	}

	public void setPwdTpl(String pwdTpl) {
		this.pwdTpl = pwdTpl;
	}

	public String getSignTpl() {
		return signTpl;
	}

	public void setSign(String signTpl) {
		this.signTpl = signTpl;
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

	public String getChangePwd() {
		return changePwd;
	}

	public void setChangePwd(String changePwd) {
		this.changePwd = changePwd;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getBirthdayTpl() {
		return birthdayTpl;
	}

	public void setBirthdayTpl(String birthdayTpl) {
		this.birthdayTpl = birthdayTpl;
	}

	public String getBirthPairs() {
		return birthPairs;
	}

	public void setBirthPairs(String birthPairs) {
		this.birthPairs = birthPairs;
	}
}
