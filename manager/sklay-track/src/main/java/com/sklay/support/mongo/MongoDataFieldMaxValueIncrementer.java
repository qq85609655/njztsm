/*
 * Project:  any
 * Module:   any-track
 * File:     MongoDataFieldMaxValueIncrementer.java
 * Modifier: xyang
 * Modified: 2012-12-25 22:25
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

import com.mongodb.DBCollection;
import com.sklay.support.incrementer.AbstractDataFieldMaxValueIncrementer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.CollectionCallback;
import org.springframework.data.mongodb.core.MongoOperations;

import static com.sklay.support.mongo.Mongos.id;
import static com.sklay.support.mongo.Mongos.obj;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-12-25
 */
public class MongoDataFieldMaxValueIncrementer extends AbstractDataFieldMaxValueIncrementer {
    private String collectionName = "_sequences";
    private MongoOperations mongoOps;

    @Autowired
    public void setMongoOps(MongoOperations mongoOps) {
        this.mongoOps = mongoOps;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    @Override
    protected long getNextKey() {
        return mongoOps.execute(collectionName, new CollectionCallback<Number>() {
            @Override
            public Number doInCollection(DBCollection collection){
                return (Number) collection.findAndModify(
                        id(incrementerName), null, null, false,
                        obj("$inc", obj(columnName, 1)),
                        true, true).get(columnName);
            }
        }).longValue();
    }
}
