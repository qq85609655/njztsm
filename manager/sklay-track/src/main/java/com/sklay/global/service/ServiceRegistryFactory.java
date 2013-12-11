/*
 * Project:  any
 * Module:   any-common-api
 * File:     ServiceRegistryFactory.java
 * Modifier: xyang
 * Modified: 2012-09-26 22:05
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

import java.util.Iterator;
import java.util.ServiceLoader;

import com.sklay.core.ex.SklayException;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-9-26
 */
public final class ServiceRegistryFactory {
    private static ServiceRegistryFactory INSTANCE = new ServiceRegistryFactory();

    public static ServiceRegistryFactory getInstance() {
        return INSTANCE;
    }

    private ServiceRegistry serviceRegistry;

    private ServiceRegistryFactory() {
        Iterator<ServiceRegistry> iterator = ServiceLoader.load(ServiceRegistry.class).iterator();
        if (iterator.hasNext()) {
            serviceRegistry = iterator.next();
        } else {
            throw new SklayException("serviceRegistry not found");
        }
    }

    public ServiceRegistry getServiceRegistry() {
        return serviceRegistry;
    }
}
