package com.sklay.model;

import java.io.Serializable;

public class HealthReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 高压值
	 */
	private Integer systolic;

	/**
	 * 低压值
	 */
	private Integer diastolic;

	/**
	 * 健康指数
	 */
	private Integer health;

	/**
	 * 健康报告
	 */
	private String report;

	public Integer getSystolic() {
		return systolic;
	}

	public void setSystolic(Integer systolic) {
		this.systolic = systolic;
	}

	public Integer getDiastolic() {
		return diastolic;
	}

	public void setDiastolic(Integer diastolic) {
		this.diastolic = diastolic;
	}

	public Integer getHealth() {
		return health;
	}

	public void setHealth(Integer health) {
		this.health = health;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public HealthReport() {
		super();
	}

	public HealthReport(Integer systolic, Integer diastolic, Integer health,
			String report) {
		super();
		this.systolic = systolic;
		this.diastolic = diastolic;
		this.health = health;
		this.report = report;
	}

	@Override
	public String toString() {
		return "HealthReport [systolic=" + systolic + ", diastolic="
				+ diastolic + ", health=" + health + ", report=" + report + "]";
	}

}
