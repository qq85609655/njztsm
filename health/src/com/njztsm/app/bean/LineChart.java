package com.njztsm.app.bean;

import java.io.Serializable;
import java.util.List;

public class LineChart extends Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<String> labels;

	private List<Line> data;

	private List<String> reports;

	private String yeah;

	private Integer vertical;

	private String member;

	private String firstTime;

	private String lastTime;

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public List<String> getReports() {
		return reports;
	}

	public void setReports(List<String> reports) {
		this.reports = reports;
	}

	public String getYeah() {
		return yeah;
	}

	public void setYeah(String yeah) {
		this.yeah = yeah;
	}

	public Integer getVertical() {
		return vertical;
	}

	public void setVertical(Integer vertical) {
		this.vertical = vertical;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public List<Line> getData() {
		return data;
	}

	public void setData(List<Line> data) {
		this.data = data;
	}

	public LineChart() {
		super();
	}

	public String getFirstTime() {
		return firstTime;
	}

	public void setFirstTime(String firstTime) {
		this.firstTime = firstTime;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public LineChart(List<String> labels, List<Line> data,
			List<String> reports, String yeah, Integer vertical, String member) {
		super();
		this.labels = labels;
		this.data = data;
		this.reports = reports;
		this.yeah = yeah;
		this.vertical = vertical;
		this.member = member;
	}

	@Override
	public String toString() {
		return "LineChart [labels=" + labels + ", data=" + data + ", reports="
				+ reports + ", yeah=" + yeah + ", vertical=" + vertical
				+ ", member=" + member + "]";
	}

}
