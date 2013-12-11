package com.sklay.vo;

import java.io.Serializable;

public class DataView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8135843335692309817L;

	private int code;

	private String msg;

	private String data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "DataView [code=" + code + ", msg=" + msg + ", data=" + data
				+ "]";
	}

	public DataView() {
		super();
	}

	public DataView(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public DataView(int code, String msg, String data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

}
