/*
 * Project:  any
 * Module:   any-track
 * File:     MobileSessionAttr.java
 * Modifier: xyang
 * Modified: 2012-11-29 08:39
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
public interface MobileSessionAttr extends SessionAttr {
    static final String CELL_ID = "cellId";
    static final String LAC = "lac";
    static final String MCC_MNC = Dicts.MCC_MNC;
    static final String NETWORK = Dicts.NETWORK;
    static final String LAT_E6 = "lat_e6";
    static final String LNG_E6 = "lng_e6";
}
