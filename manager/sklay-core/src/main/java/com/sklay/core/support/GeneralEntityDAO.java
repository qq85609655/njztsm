/*
 * Project:  any-parent
 * Module:   any-framework
 * File:     GeneralEntityDAO.java
 * Modifier: ozn
 * Modified: 2012-08-12 15:51
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.core.support;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import com.sklay.core.ex.SklayException;

/**
 * 
 *  .
 * <p/>
 *
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-8-9
 */
public interface GeneralEntityDAO<E, PK extends Serializable> {

    E save(E entity) throws SklayException;

    void deleteByPk(PK id) throws SklayException;

    void delete(E entity);

    void batchDeleteByPK(Collection<PK> ids);

    void batchDelete(Collection<E> entities);

    E get(PK id);

    E load(PK id) throws EntityNotFoundException;

    Map<PK, E> batchGet(Collection<PK> ids);

    List<E> getAll();
}
