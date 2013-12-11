/*
 * Project:  any
 * Module:   any-track
 * File:     AbstractDataFieldMaxValueIncrementer.java
 * Modifier: xyang
 * Modified: 2012-12-25 22:19
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.support.incrementer;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-12-25
 */
public abstract class AbstractDataFieldMaxValueIncrementer implements DataFieldMaxValueIncrementer, InitializingBean {
    public static final String NEXT = "next";
    protected String incrementerName;
    protected String columnName = NEXT;
    protected int paddingLength = 0;

    public void setIncrementerName(String incrementerName) {
        this.incrementerName = incrementerName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setPaddingLength(int paddingLength) {
        this.paddingLength = paddingLength;
    }

    public int getPaddingLength() {
        return this.paddingLength;
    }

    public void afterPropertiesSet() {
        if (this.incrementerName == null) {
            throw new IllegalArgumentException("Property 'incrementerName' is required");
        }
    }

    public int nextIntValue() throws DataAccessException {
        return (int) getNextKey();
    }

    public long nextLongValue() throws DataAccessException {
        return getNextKey();
    }

    public String nextStringValue() throws DataAccessException {
        String s = Long.toString(getNextKey());
        int len = s.length();
        if (len < this.paddingLength) {
            StringBuilder sb = new StringBuilder(this.paddingLength);
            for (int i = 0; i < this.paddingLength - len; i++) {
                sb.append('0');
            }
            sb.append(s);
            s = sb.toString();
        }
        return s;
    }

    protected abstract long getNextKey();
}
