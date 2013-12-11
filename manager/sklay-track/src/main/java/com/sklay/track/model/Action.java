/*
 * Project:  any
 * Module:   any-track
 * File:     Action.java
 * Modifier: xyang
 * Modified: 2012-11-26 14:39
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

import com.sklay.track.model.attr.ActionAttr;

import java.util.Date;

/**
 * 事件的动作,用来做更详细的行为分析,例如用户点击,和event是一对多关系
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-11-26
 */
public class Action extends Entity<String> implements ActionAttr {
    private static final long serialVersionUID = 5792568280636888434L;
    @Indexed
    private String eventId;
    @Indexed
    private int type;
    private String name;
    @Indexed
    private Date happenAt;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getHappenAt() {
        return happenAt;
    }

    public void setHappenAt(Date happenAt) {
        this.happenAt = happenAt;
    }
}
