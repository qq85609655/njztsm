package com.sklay.model;

import java.io.Serializable;

public class LineEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fillColor; // : "rgba(220,220,220,0.5)",

	private String strokeColor; // : "rgba(220,220,220,1)",

	private String pointColor; // : "rgba(220,220,220,1)",

	private String pointStrokeColor; // : "#fff",

	private String data; // data : [65,59,90,81,56,55,40]

	public String getFillColor() {
		return fillColor;
	}

	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}

	public String getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(String strokeColor) {
		this.strokeColor = strokeColor;
	}

	public String getPointColor() {
		return pointColor;
	}

	public void setPointColor(String pointColor) {
		this.pointColor = pointColor;
	}

	public String getPointStrokeColor() {
		return pointStrokeColor;
	}

	public void setPointStrokeColor(String pointStrokeColor) {
		this.pointStrokeColor = pointStrokeColor;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public LineEntity() {
		super();
	}

	public LineEntity(String fillColor, String strokeColor, String pointColor,
			String pointStrokeColor, String data) {
		super();
		this.fillColor = fillColor;
		this.strokeColor = strokeColor;
		this.pointColor = pointColor;
		this.pointStrokeColor = pointStrokeColor;
		this.data = data;
	}

	@Override
	public String toString() {
		return "LineEntity [fillColor=" + fillColor + ", strokeColor="
				+ strokeColor + ", pointColor=" + pointColor
				+ ", pointStrokeColor=" + pointStrokeColor + ", data=" + data
				+ "]";
	}

}
