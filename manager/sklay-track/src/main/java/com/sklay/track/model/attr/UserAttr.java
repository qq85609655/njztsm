/*
 * Project:  any
 * Module:   any-track
 * File:     UserAttr.java
 * Modifier: xyang
 * Modified: 2012-11-28 16:06
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents   static final String thereof = "thereof"); are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility   static final String model = "model"); design or code.
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
public interface UserAttr {
    static final String GENDER = Dicts.GENDER;
    static final String ID_NO = "idNo";
    static final String LOCATION = "location";
    static final String SCHOOL = "school";
    static final String GRADE = Dicts.GRADE;
    static final String BIRTHDAY = "birthday";
    static final String AGE = "age";
    static final String EMAIL = "email";
    static final String MOBILE = "mobile";
}
