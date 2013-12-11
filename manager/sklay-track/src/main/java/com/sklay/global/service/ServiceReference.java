/*
 * Project:  any
 * Module:   any-common-api
 * File:     ServiceReference.java
 * Modifier: xyang
 * Modified: 2012-09-26 17:26
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.global.service;

import com.sklay.core.ex.SklayException;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-9-26
 */
public interface ServiceReference<T> {

    T getService() throws SklayException;

    void destroy();
}
