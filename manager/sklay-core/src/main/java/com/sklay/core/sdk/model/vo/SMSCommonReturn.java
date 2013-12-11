package com.sklay.core.sdk.model.vo;

import java.io.Serializable;

public class SMSCommonReturn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4531759797709976132L;

	private String retCode ;
	
	private String message ;

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

	@Override
	public String toString() {
		return "CommonReturn [retCode=" + retCode + ", message=" + message
				+ "]";
	}
}
