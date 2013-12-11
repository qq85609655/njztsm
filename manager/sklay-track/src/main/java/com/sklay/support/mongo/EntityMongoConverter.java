/*
 * Project:  any
 * Module:   any-track
 * File:     EntityMongoConverter.java
 * Modifier: xyang
 * Modified: 2012-12-25 21:58
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

import com.mongodb.DBObject;
import com.sklay.track.model.Entity;

import org.springframework.data.mapping.PropertyHandler;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-12-25
 */
public class EntityMongoConverter extends MappingMongoConverter {
    private final ConcurrentMap<Class, Set<String>> ignorePropertiesMap = new ConcurrentHashMap<Class, Set<String>>();

    public EntityMongoConverter(MongoDbFactory mongoDbFactory, MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext) {
        super(mongoDbFactory, mappingContext);
    }

    @Override
    public void write(Object obj, DBObject dbo) {
        if (null == obj) {
            return;
        }
        writeInternal(obj, dbo, ClassTypeInformation.from(obj.getClass()));
        if (obj instanceof Entity) {
            writeAttrs((Entity) obj, dbo);
        }
    }

    @Override
    protected <S> S read(TypeInformation<S> type, DBObject dbo, Object parent) {
        final S result = super.read(type, dbo, parent);
        if (result != null && result instanceof Entity) {
            readAttrs((Entity) result, dbo, getIgnoreProperties(type, dbo));
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private Set<String> getIgnoreProperties(TypeInformation type, DBObject dbo) {
        Set<String> ignoreProperties = ignorePropertiesMap.get(type.getType());
        if (ignoreProperties == null) {
            TypeInformation typeToUse = typeMapper.readType(dbo, type);
            MongoPersistentEntity persistentEntity = mappingContext.getPersistentEntity(typeToUse);
            final Set<String> set = new HashSet<String>();
            persistentEntity.doWithProperties(new PropertyHandler<MongoPersistentProperty>() {
                @Override
                public void doWithPersistentProperty(MongoPersistentProperty persistentProperty) {
                    set.add(persistentProperty.getFieldName());
                }
            });
            ignorePropertiesMap.putIfAbsent(type.getType(), set);
            return set;
        }
        return ignoreProperties;
    }

    private void writeAttrs(Entity<?> entity, DBObject dbo) {
        for (Map.Entry<String, Object> entry : entity.getAttrs().entrySet()) {
            String field = entry.getKey();
            if (!dbo.containsField(field)) {
                dbo.put(field, entry.getValue());
            }
        }
    }

    private void readAttrs(Entity<?> entity, DBObject dbo, Set<String> ignoreProperties) {
        for (String key : dbo.keySet()) {
            if (!ignoreProperties.contains(key)) {
                entity.setAttr(key, dbo.get(key));
            }
        }
    }
}
