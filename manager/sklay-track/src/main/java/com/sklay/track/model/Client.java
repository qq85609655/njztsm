/*
 * Project:  any
 * Module:   any-track
 * File:     Client.java
 * Modifier: xyang
 * Modified: 2012-11-26 14:23
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

import com.sklay.track.model.attr.ClientAttr;

import java.util.Date;

/**
 * 被跟踪的客户端,例如浏览器,移动终端
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-11-26
 */
public class Client extends Entity<String> implements ClientAttr {
    private static final long serialVersionUID = -2932101223499042341L;
    @Indexed
    private Date createAt;

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
