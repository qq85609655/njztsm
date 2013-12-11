/*
 * Project:  any
 * Module:   any-track
 * File:     Event.java
 * Modifier: xyang
 * Modified: 2012-11-26 14:43
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

import com.sklay.track.model.attr.EventAttr;

import java.util.Date;

/**
 * 跟踪会话期间发生的事件,和session是一对多关系
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-11-26
 */
public class Event extends Entity<String> implements EventAttr {
    public static final int EVENT_BEGIN = 0;
    public static final int EVENT_END = 1;
    public static final int EVENT_HEARTBEAT = 2;
    public static final int EVENT_READ = 3;

    private static final long serialVersionUID = 2761687302116926168L;
    @Indexed
    private String appId;
    @Indexed
    private String clientId;
    @Indexed
    private String sessionId;
    private String fromEventId;
    private String appSessionId;
    private Boolean firstView;
    @Indexed
    private String userId;
    private String[] tags;
    private String url;
    @Indexed
    private Date beginAt;
    @Indexed
    private Date endAt;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getFromEventId() {
        return fromEventId;
    }

    public void setFromEventId(String fromEventId) {
        this.fromEventId = fromEventId;
    }

    public String getAppSessionId() {
        return appSessionId;
    }

    public void setAppSessionId(String appSessionId) {
        this.appSessionId = appSessionId;
    }

    public Boolean getFirstView() {
        return firstView;
    }

    public void setFirstView(Boolean firstView) {
        this.firstView = firstView;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public int getDuration() {
    	if(beginAt==null){
    		return 0;
    	}
        return (int) ((endAt == null ? System.currentTimeMillis() : endAt.getTime()) - beginAt.getTime());
    }
}
