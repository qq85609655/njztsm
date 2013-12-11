/*
 * Project:  any
 * Module:   any-track
 * File:     MongoEntityTemplate.java
 * Modifier: xyang
 * Modified: 2012-12-25 21:55
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

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;


import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.core.support.GeneralEntityDAO;
import com.sklay.core.util.ClassUtils;
import com.sklay.support.incrementer.DataFieldMaxValueIncrementer;
import com.sklay.track.model.Entity;

/**
 * 
 * .
 * <p/>
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-8-9
 */
@SuppressWarnings("unchecked")
public class EntityMongoTemplate<E extends Entity<PK>, PK extends Serializable>
		implements GeneralEntityDAO<E, PK> {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	protected MongoTemplate mongo;
	private DataFieldMaxValueIncrementer incrementer;
	protected Class<E> entityClass;

	public EntityMongoTemplate() {
		this.entityClass = ClassUtils.getGenericParameter0(getClass());
	}

	public void setMongo(MongoTemplate mongo) {
		this.mongo = mongo;
	}

	public void setIncrementer(DataFieldMaxValueIncrementer incrementer) {
		this.incrementer = incrementer;
	}

	public MongoTemplate getMongo() {
		return mongo;
	}

	@Override
	public E save(E entity) throws SklayException {
		if (entity.getId() == null && incrementer != null) {
			// entity.setId(incrementer.nextLongValue());
		}
		mongo.save(entity);
		return entity;
	}

	@Override
	public void deleteByPk(PK id) throws EntityNotFoundException {
		mongo.remove(query(where(Mongos.ID).is(id)));
	}

	@Override
	public void delete(E entity) {
		mongo.remove(entity);
	}

	@Override
	public void batchDeleteByPK(Collection<PK> ids) {
		mongo.remove(query(where(Mongos.ID).in(ids)));
	}

	@Override
	public void batchDelete(Collection<E> entities) {
		List<PK> ids = new ArrayList<PK>(entities.size());
		for (E entity : entities) {
			ids.add(entity.getId());
		}
		batchDeleteByPK(ids);
	}

	@Override
	public E get(PK id) {
		return mongo.findById(id, entityClass);
	}

	@Override
	public E load(PK id) throws SklayException {
		E entity = get(id);
		if (entity == null) {
			throw new SklayException(ErrorCode.FINF_NULL, null, entityClass, id);
		}
		return entity;
	}

	@Override
	public Map<PK, E> batchGet(Collection<PK> ids) {
		List<E> list = mongo.find(query(where(Mongos.ID).in(ids)), entityClass);
		Map<PK, E> map = new HashMap<PK, E>(list.size());
		for (E entity : list) {
			map.put(entity.getId(), entity);
		}
		return map;
	}

	@Override
	public List<E> getAll() {
		return mongo.findAll(entityClass);
	}
}
