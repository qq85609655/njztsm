/*
 * Project:  any
 * Module:   any-track
 * File:     Item.java
 * Modifier: xyang
 * Modified: 2012-11-28 21:14
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.track.model.dict;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.index.Indexed;

import com.sklay.core.annotation.Keyable;
import com.sklay.track.model.Entity;


import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 
 * .
 * <p/>
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-8-9
 */
public class Item extends Entity<Integer> implements Keyable<String>,
		Comparable<Item>, Serializable {
	private static final long serialVersionUID = -3180836015295650089L;
	@Indexed
	private Integer dictId;
	@Indexed
	private Integer parentId;
	@Indexed
	private String key;
	private String value;
	private String desc;
	private int order = 100;
	private Date createAt;

	public Item(Integer parentId, Map<String, Object> attrs) {
		this(attrs);
		this.parentId = parentId;
	}

	public Item(Map<String, Object> attrs) {
		setAttrs(attrs);
	}

	public Item(Integer parentId) {
		this.parentId = parentId;
	}

	public Item() {
	}

	public Integer getDictId() {
		return dictId;
	}

	public void setDictId(Integer dictId) {
		this.dictId = dictId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int compareTo(Item o) {
		return order - o.getOrder();
	}
}
