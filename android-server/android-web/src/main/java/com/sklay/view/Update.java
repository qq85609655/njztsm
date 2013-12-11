package com.sklay.view;

import java.io.Serializable;

/**
 * 应用程序更新实体类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class Update implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1356876892850891288L;

	private int versionCode;
	private String versionName;
	private String downloadUrl;
	private String updateLog;

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getUpdateLog() {
		return updateLog;
	}

	public void setUpdateLog(String updateLog) {
		this.updateLog = updateLog;
	}

	@Override
	public String toString() {
		return "Update [versionCode=" + versionCode + ", versionName="
				+ versionName + ", downloadUrl=" + downloadUrl + ", updateLog="
				+ updateLog + "]";
	}

	public Update() {
		super();
	}

	public Update(int versionCode, String versionName, String downloadUrl,
			String updateLog) {
		super();
		this.versionCode = versionCode;
		this.versionName = versionName;
		this.downloadUrl = downloadUrl;
		this.updateLog = updateLog;
	}

}
