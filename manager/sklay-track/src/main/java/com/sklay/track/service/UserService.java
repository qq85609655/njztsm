/*
 * Project:  any
 * Module:   any-track
 * File:     UserService.java
 * Modifier: xyang
 * Modified: 2012-12-22 21:51
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


import java.util.Date;

import com.sklay.track.model.User;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-12-22
 */
public interface UserService {
    User saveUser(User user);

    User saveUserIfNotExist(User user);

    User getUser(String id);

    void reloadUsers(Date startTime, Date endTime);
}
