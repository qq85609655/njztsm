/*
 * Project:  any
 * Module:   any-track
 * File:     BrowserActionAttr.java
 * Modifier: xyang
 * Modified: 2012-11-29 09:26
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.track.model.attr;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-11-28
 */
public interface ActionAttr {
    /**
     * 点击
     */
    static final int CLICK = 0;
    /**
     * 站内点击
     */
    static final int INNNER_CLICK = 1;
    /**
     * 跳出点击
     */
    static final int OUTER_CLICK = 2;
    /**
     * 输入
     */
    static final int INPUT = 3;
    /**
     * 远程请求
     */
    static final int REQUEST = 4;
    /**
     * 自定义事件
     */
    static final int CUSTOM = 100;

    /**
     * 事件发生的坐标,格式x,y,屏幕宽度
     */
    static final String POS = "pos";
    /**
     * 输入的内容
     */
    static final String VALUE = "text";
    /**
     * 发生事件的组件id
     */
    static final String TARGET_ID = "tid";
    /**
     * 目标的链接地址
     */
    static final String TARGET_URL = "url";
    /**
     * 远程请求消耗的时间
     */
    static final String LOAD_TIME = "lt";
}
