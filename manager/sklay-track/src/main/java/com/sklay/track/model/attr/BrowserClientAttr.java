/*
 * Project:  any
 * Module:   any-track
 * File:     BrowserClientAttr.java
 * Modifier: xyang
 * Modified: 2012-11-28 16:02
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
 * @version V1.0, 12-11-28
 */
public interface BrowserClientAttr extends ClientAttr {
    static final String AGENT = "agent";
    static final String BROWSER = Dicts.BROWSER;
    static final String BROWSER_VERSION = Dicts.BROWSER_VERSION;
    static final String COLOR_DEPTH = Dicts.COLOR_DEPTH;
    static final String SCREEN = Dicts.SCREEN;
    static final String FLASH_VERSION = Dicts.FLASH_VERSION;
}
