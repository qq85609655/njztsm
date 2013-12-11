/*
 * Project:  any
 * Module:   any-track
 * File:     DataFieldMaxValueIncrementer.java
 * Modifier: xyang
 * Modified: 2012-12-25 22:23
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.support.incrementer;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-12-25
 */
public interface DataFieldMaxValueIncrementer {
    int nextIntValue();

    long nextLongValue();

    String nextStringValue();
}