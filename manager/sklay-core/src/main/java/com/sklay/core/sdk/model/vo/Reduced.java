package com.sklay.core.sdk.model.vo;

import java.io.Serializable;

public class Reduced implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3231055443788370728L;

	private String errPhones;

	private String jobID;

	private String retCode;

	private Integer OKPhoneCounts;

	private String message;

	public String getErrPhones() {
		return errPhones;
	}

	public void setErrPhones(String errPhones) {
		this.errPhones = errPhones;
	}

	public String getJobID() {
		return jobID;
	}

	public void setJobID(String jobID) {
		this.jobID = jobID;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getOKPhoneCounts() {
		return OKPhoneCounts;
	}

	public void setOKPhoneCounts(Integer oKPhoneCounts) {
		OKPhoneCounts = oKPhoneCounts;
	}

	@Override
	public String toString() {
		return "Reduced [errPhones=" + errPhones + ", jobID=" + jobID
				+ ", retCode=" + retCode + ", oKPhoneCounts=" + OKPhoneCounts
				+ ", message=" + message + "]";
	}

}
