/*
 * Project:  any
 * Module:   framework
 * File:     Page.java
 * Modifier: xyang
 * Modified: 2012-07-03 21:08
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.track.util;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 11-12-30
 */
public class Page<T> implements Serializable {
    private static final long serialVersionUID = 399508739687121397L;

    public static final int ALL = -1;
    public static final int NONE = 0;
    public static final int SHOW_PAGES = 8;
    public static final int DEFAULT_SIZE = 20;
    protected int totalCount = 0;
    protected List<T> items = Collections.emptyList();
    private int index = 0;
    private int size = DEFAULT_SIZE;

    public Page() {
    }

    public Page(int index) {
        this.index = index;
    }

    public Page(List<T> items) {
        this.items = items;
    }

    public Page(int index, int size) {
        this(index);
        this.size = size;
    }

    public Page(List<T> items, int totalCount, int start, int size) {
        this.items = items;
        this.totalCount = totalCount;
        this.index = toIndex(start, size);
        this.size = size;
    }

    @JSONField(serialize = false)
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @JSONField(serialize = false)
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return totalCount;
    }

    public void setTotal(int totalCount) {
        this.totalCount = totalCount;
    }

    public T getItem(int index) {
        return items.get(index);
    }

    @JSONField(serialize = false)
    public List<Integer> getShowPages() {
        List<Integer> pages = new ArrayList<Integer>();
        int start = index - SHOW_PAGES;
        int end = index + SHOW_PAGES;
        int total = getPageCount();
        start = start > 0 ? start : 1;
        end = end > total ? total : end;
        for (int i = start; i <= end; i++) {
            pages.add(i);
        }
        return pages;
    }

    @JSONField(serialize = false)
    public int getPageCount() {
        int count = totalCount / size;
        return totalCount % size != 0 ? count + 1 : count;
    }

    public void setStart(int start) {
        this.index = start / size;
    }

    @JSONField(serialize = false)
    public boolean isEmpty() {
        return totalCount == 0;
    }

    public static int toIndex(int start, int size) {
        if (size == 0)
            return 1;
        return start / size + 1;
    }

    public static int toStart(Integer index, int size) {
        return index == null ? 0 : (index - 1) * size;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
