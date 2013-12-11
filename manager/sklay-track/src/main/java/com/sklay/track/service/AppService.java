/*
 * Project:  any
 * Module:   any-track
 * File:     AppManager.java
 * Modifier: xyang
 * Modified: 2012-12-22 20:07
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.track.service;


import java.util.List;

import com.sklay.track.model.App;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-12-22
 */
public interface AppService {
    App saveApp(App app);

    void removeApp(String id);

    App getApp(String id);

    App getAppEx(String id);

    App getAppByKey(String appKey);

    List<App> getApps();
}
