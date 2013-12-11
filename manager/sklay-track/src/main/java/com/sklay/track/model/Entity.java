/*
 * Project:  any
 * Module:   any-track
 * File:     Base.java
 * Modifier: xyang
 * Modified: 2012-11-28 19:44
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

import org.apache.commons.lang.builder.ToStringBuilder;

import com.sklay.track.attr.AbstractAttrable;
import com.sklay.track.attr.Attrable;

import java.io.Serializable;

/**
 * 基础对象
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-11-28
 */
public class Entity<PK> extends AbstractAttrable implements Serializable, Attrable {
    private static final long serialVersionUID = -4007582226596990539L;
    private PK id;

    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
