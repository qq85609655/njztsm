/*
 * Project:  any
 * Module:   any-track
 * File:     MobileClient.java
 * Modifier: xyang
 * Modified: 2012-11-28 16:03
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
public interface MobileClientAttr extends ClientAttr {
    static final String CHANNEL = Dicts.CHANNEL;

    static final String MANUFACTURER = Dicts.MANUFACTURER;
    static final String MODEL = Dicts.MODEL;
    static final String PRODUCT = Dicts.PRODUCT;

    static final String ROM_SIZE = "rom";
    static final String RAM_SIZE = "ram";
    static final String SD_SIZE = "sd";
    static final String CPU = Dicts.CPU;
    static final String CPU_FREQUENCY = "cpuFrequency";

    static final String DEVICE_ID = "deviceId";
    static final String WIFI_MAC = "wifiMac";
    static final String IMSI = "imsi";
    static final String IMEI = "imei";
    static final String HAS_BLUE_TOOTH_ADAPTER = "bt";
    static final String HAS_WIFI = "wifi";
    static final String HAS_GPS = "gps";
    static final String HAS_GRAVITY = "gravity";

    static final String MCC_MNC = Dicts.MCC_MNC;
    static final String NETWORK = Dicts.NETWORK;
    static final String LAT_E6 = "lat_e6";
    static final String LNG_E6 = "lng_e6";
}
