/*
 * Project:  any
 * Module:   any-common-api
 * File:     ServiceRegistry.java
 * Modifier: xyang
 * Modified: 2012-09-26 17:01
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.global.service;

import java.util.Collection;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-9-26
 */
public interface ServiceRegistry {

    <T> void exportService(Class<T> type, T service);

    <T> void unexportService(Class<T> type);

    <T> ServiceReference<T> getService(Class<T> type);

    Collection<ServiceReference> getServices();
}
