/*
 * Project:  any
 * Module:   any-track
 * File:     User.java
 * Modifier: xyang
 * Modified: 2012-11-28 16:05
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


import java.util.Date;

import com.sklay.track.model.attr.UserAttr;

/**
 * 当前跟踪会话对应的用户
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-11-28
 */
public class User extends Entity<String> implements UserAttr {
    private static final long serialVersionUID = -566562631149807619L;
    private String name;
    private String nickName;
    private String realName;
    private Date createAt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
