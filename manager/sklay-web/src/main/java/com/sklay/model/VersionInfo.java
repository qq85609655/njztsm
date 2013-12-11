package com.sklay.model;

import java.io.Serializable;

public class VersionInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7753377113221284281L;

	// 版本描述字符串
	private String version;
	// 版本更新时间
	private String updateTime;
	// 新版本更新下载地址
	private String downloadURL;
	// 更新描述信息
	private String displayMessage;
	// 版本号
	private int versionCode;
	// apk名称
	private String apkName;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getDownloadURL() {
		return downloadURL;
	}

	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}

	public String getDisplayMessage() {
		return displayMessage;
	}

	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getApkName() {
		return apkName;
	}

	public void setApkName(String apkName) {
		this.apkName = apkName;
	}

	@Override
	public String toString() {
		return "VersionInfo [version=" + version + ", updateTime=" + updateTime
				+ ", downloadURL=" + downloadURL + ", displayMessage="
				+ displayMessage + ", versionCode=" + versionCode
				+ ", apkName=" + apkName + "]";
	}

	public VersionInfo() {
		super();
	}

	public VersionInfo(String version, String updateTime, String downloadURL,
			String displayMessage, int versionCode, String apkName) {
		super();
		this.version = version;
		this.updateTime = updateTime;
		this.downloadURL = downloadURL;
		this.displayMessage = displayMessage;
		this.versionCode = versionCode;
		this.apkName = apkName;
	}

}
