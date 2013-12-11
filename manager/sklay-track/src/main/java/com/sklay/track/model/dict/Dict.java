/*
 * Project:  any
 * Module:   any-track
 * File:     Dict.java
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

import org.springframework.data.mongodb.core.index.Indexed;

import com.sklay.track.model.Entity;

import java.util.Date;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-11-28
 */
public class Dict extends Entity<Integer> {
    private static final long serialVersionUID = 8526631947772678741L;
    @Indexed
    private String key;
    private String name;
    private Date createAt;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
