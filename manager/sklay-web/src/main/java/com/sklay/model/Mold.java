package com.sklay.model;

import java.io.Serializable;
import java.util.Date;

import com.sklay.core.enums.MoldType;

public class Mold implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date startTime;

	private Date endTime;

	/** 付费类型 */
	private MoldType moldType;

	private long cost;

	private double price;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public MoldType getMoldType() {
		return moldType;
	}

	public void setMoldType(MoldType moldType) {
		this.moldType = moldType;
	}

	public long getCost() {
		return cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Mold() {
		super();
	}

	public Mold(Date startTime, Date endTime, MoldType moldType) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.moldType = moldType;
	}

	public Mold(Date startTime, Date endTime, MoldType moldType, long cost,
			double price) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.moldType = moldType;
		this.cost = cost;
		this.price = price;
	}

	@Override
	public String toString() {
		return "Mold [startTime=" + startTime + ", endTime=" + endTime
				+ ", moldType=" + moldType + ", cost=" + cost + ", price="
				+ price + "]";
	}

}
