/*
 * Project:  any
 * Module:   any-track
 * File:     Session.java
 * Modifier: xyang
 * Modified: 2012-11-26 14:21
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.track.model;

import org.springframework.data.mongodb.core.index.Indexed;

import com.sklay.track.model.attr.SessionAttr;

import java.util.Date;

/**
 * 跟踪会话,和client是一对多关系
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-11-26
 */
public class Session extends Entity<String> implements SessionAttr {
    private static final long serialVersionUID = 1541972377321379349L;
    @Indexed
    private String clientId;
    @Indexed
    private Date beginAt;
    private Date endAt;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Date getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(Date beginAt) {
        this.beginAt = beginAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    public long getDuration() {
        return (int)((endAt == null ? System.currentTimeMillis() : endAt.getTime()) - beginAt.getTime());
    }
}
