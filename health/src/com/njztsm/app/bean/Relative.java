package com.njztsm.app.bean;

import java.io.Serializable;

public class Relative implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String title;

	public String url;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Relative [title=" + title + ", url=" + url + "]";
	}
}