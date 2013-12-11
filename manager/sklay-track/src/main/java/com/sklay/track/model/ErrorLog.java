/*
 * Project:  any
 * Module:   any-track
 * File:     ErrorLog.java
 * Modifier: xyang
 * Modified: 2012-11-26 14:33
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

import java.util.Date;

/**
 * 错误汇报
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-11-26
 */
public class ErrorLog extends Entity<String> {
    private static final long serialVersionUID = -8472021204151806427L;
    private String eventId;
    private String msg;
    private boolean fixed;
    @Indexed
    private Date happenAt;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public Date getHappenAt() {
        return happenAt;
    }

    public void setHappenAt(Date happenAt) {
        this.happenAt = happenAt;
    }
}
