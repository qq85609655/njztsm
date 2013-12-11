package com.sklay.core.sdk.model.vo;

import java.io.Serializable;

public class SMSLoginInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3839105205165301672L;

	private String retCode;

	private String message;

	private String userID;

	private String account;

	private String token;

	private String alive;

	private String version;

	private String smsServerAddress;

	private int userType;

	private int userRight;

	private int agentID;

	private int segmentUpperLimit;

	private int longSmsLen;

	private int shortSmsLen;

	private int fetchSendStat;

	private int smsStock;

	private String password;

	/**
	 * 若操作成功则为Sucess，否则为Faild
	 * 
	 * @return
	 */
	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	/**
	 * 若操作失败，则表示错误信息，否则为空
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 登录的用户ID
	 * 
	 * @return
	 */
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * 登录的子账号名称
	 * 
	 * @return
	 */
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 用户指纹（非常重要，用户据此执行其他操作）
	 * 
	 * @return
	 */
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * 最后与服务器心跳同步的时间(格式为hh:mm)
	 * 
	 * @return
	 */
	public String getAlive() {
		return alive;
	}

	public void setAlive(String alive) {
		this.alive = alive;
	}

	/**
	 * 最新客户端的版本号
	 * 
	 * @return
	 */
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * 短信平台服务器地址
	 * 
	 * @param version
	 */
	public String getSmsServerAddress() {
		return smsServerAddress;
	}

	public void setSmsServerAddress(String smsServerAddress) {
		this.smsServerAddress = smsServerAddress;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	/**
	 * 用户操作权限(0：没有权限；1：单用户、发送；2：单用户、发送接收；3：多用户、发送；4：多用户、发送接收)
	 * 
	 * @return
	 */
	public int getUserRight() {
		return userRight;
	}

	public void setUserRight(int userRight) {
		this.userRight = userRight;
	}

	/**
	 * 
	 * @return
	 */
	public int getAgentID() {
		return agentID;
	}

	public void setAgentID(int agentID) {
		this.agentID = agentID;
	}

	/**
	 * 群发时，每个号段中允许的最多号码数量
	 * 
	 * @return
	 */
	public int getSegmentUpperLimit() {
		return segmentUpperLimit;
	}

	public void setSegmentUpperLimit(int segmentUpperLimit) {
		this.segmentUpperLimit = segmentUpperLimit;
	}

	/**
	 * 移动、联通短信的短信长度
	 * 
	 * @return
	 */
	public int getLongSmsLen() {
		return longSmsLen;
	}

	public void setLongSmsLen(int longSmsLen) {
		this.longSmsLen = longSmsLen;
	}

	/**
	 * 小灵通短信长度
	 * 
	 * @return
	 */
	public int getShortSmsLen() {
		return shortSmsLen;
	}

	public void setShortSmsLen(int shortSmsLen) {
		this.shortSmsLen = shortSmsLen;
	}

	/**
	 * 是否需要获取短信发送状态 0 表示否 1 表示是
	 * 
	 * @return
	 */
	public int getFetchSendStat() {
		return fetchSendStat;
	}

	public void setFetchSendStat(int fetchSendStat) {
		this.fetchSendStat = fetchSendStat;
	}

	/**
	 * 短信库余量
	 * 
	 * @return
	 */
	public int getSmsStock() {
		return smsStock;
	}

	public void setSmsStock(int smsStock) {
		this.smsStock = smsStock;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "SMSLoginInfo [retCode=" + retCode + ", message=" + message
				+ ", userID=" + userID + ", account=" + account + ", token="
				+ token + ", alive=" + alive + ", version=" + version
				+ ", smsServerAddress=" + smsServerAddress + ", userType="
				+ userType + ", userRight=" + userRight + ", agentID="
				+ agentID + ", segmentUpperLimit=" + segmentUpperLimit
				+ ", longSmsLen=" + longSmsLen + ", shortSmsLen=" + shortSmsLen
				+ ", fetchSendStat=" + fetchSendStat + ", smsStock=" + smsStock
				+ ", password=" + password + "]";
	}

}
