package com.njztsm.app.bean;

import java.io.Serializable;
import java.util.List;

public class Line implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private List<Integer> value;
	private String color;
	private Integer line_width;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Integer> getValue() {
		return value;
	}

	public void setValue(List<Integer> value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getLine_width() {
		return line_width;
	}

	public void setLine_width(Integer line_width) {
		this.line_width = line_width;
	}

	public Line() {
		super();
	}

	public Line(String name, List<Integer> value, String color,
			Integer line_width) {
		super();
		this.name = name;
		this.value = value;
		this.color = color;
		this.line_width = line_width;
	}

	@Override
	public String toString() {
		return "Line [name=" + name + ", value=" + value + ", color=" + color
				+ ", line_width=" + line_width + "]";
	}
}
