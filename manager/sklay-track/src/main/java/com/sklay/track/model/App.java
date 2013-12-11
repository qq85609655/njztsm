/*
 * Project:  any
 * Module:   any-track
 * File:     App.java
 * Modifier: xyang
 * Modified: 2012-11-26 14:35
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


import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sklay.track.model.attr.AppAttr;

/**
 * 被跟踪的应用
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-11-26
 */
public class App extends Entity<String> implements AppAttr {
    private static final long serialVersionUID = 1785396232747742740L;
    private String key;
    private AppType type = AppType.BROWSER;
    private String name;
    private int sessionTimeout;
    private String description;
    private Map<String, Serializable> configs = new HashMap<String, Serializable>();
    private Date createAt;
    private long lastModified;
    private boolean enabled = true;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public AppType getType() {
        return type;
    }

    public void setType(AppType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Serializable> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<String, Serializable> configs) {
        this.configs = configs;
    }

    public void setConfig(String key, Serializable value) {
        configs.put(key, value);
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
