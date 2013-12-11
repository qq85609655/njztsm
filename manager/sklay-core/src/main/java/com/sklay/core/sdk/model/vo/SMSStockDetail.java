package com.sklay.core.sdk.model.vo;

import java.io.Serializable;

public class SMSStockDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5579715740528495433L;

	private String retCode;

	private String message;

	private String stockRemain;

	private String points;

	private String sendTotal;

	private String curDaySend;

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

	public String getStockRemain() {
		return stockRemain;
	}

	public void setStockRemain(String stockRemain) {
		this.stockRemain = stockRemain;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getSendTotal() {
		return sendTotal;
	}

	public void setSendTotal(String sendTotal) {
		this.sendTotal = sendTotal;
	}

	public String getCurDaySend() {
		return curDaySend;
	}

	public void setCurDaySend(String curDaySend) {
		this.curDaySend = curDaySend;
	}

	@Override
	public String toString() {
		return "StockDetail [retCode=" + retCode + ", message=" + message
				+ ", stockRemain=" + stockRemain + ", points=" + points
				+ ", sendTotal=" + sendTotal + ", curDaySend=" + curDaySend
				+ "]";
	}

}
