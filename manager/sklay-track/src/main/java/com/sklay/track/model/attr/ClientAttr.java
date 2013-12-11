/*
 * Project:  any
 * Module:   any-track
 * File:     ClientAttr.java
 * Modifier: xyang
 * Modified: 2012-11-29 15:27
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

import com.sklay.track.model.dict.Dicts;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-11-29
 */
public interface ClientAttr {
    static final String IP = "ip";
    static final String OS = Dicts.OS;
    static final String OS_VERSION = Dicts.OS_VERSION;
    static final String LANG = Dicts.LANG;
    static final String SCREEN = Dicts.SCREEN;
}
