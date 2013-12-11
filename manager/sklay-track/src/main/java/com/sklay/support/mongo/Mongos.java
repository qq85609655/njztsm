/*
 * Project:  any
 * Module:   any-track
 * File:     Mongos.java
 * Modifier: xyang
 * Modified: 2012-12-26 21:39
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.support.mongo;

import com.mongodb.BasicDBObject;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-12-26
 */
public final class Mongos {
    private Mongos() {
    }

    public static final String ID = "_id";

    public static Criteria idIs(String id) {
        return where(ID).is(id);
    }

    public static Query idQuery(String id) {
        return Query.query(idIs(id));
    }

    public static BasicDBObject id(Object id) {
        return new BasicDBObject().append(ID, id);
    }

    public static BasicDBObject obj(String key, Object val) {
        return new BasicDBObject().append(key, val);
    }
}
