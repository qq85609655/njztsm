package com.sklay.view;

import java.io.Serializable;

/**
 * 实体类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public abstract class Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected int id;

	protected Notice notice;

	protected String cacheKey;

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public int getId() {
		return id;
	}

	public String getCacheKey() {
		return cacheKey;
	}

	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}

	public void setId(int id) {
		this.id = id;
	}
}
