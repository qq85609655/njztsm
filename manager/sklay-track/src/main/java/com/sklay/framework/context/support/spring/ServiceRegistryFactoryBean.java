/*
 * Project:  any
 * Module:   any-framework
 * File:     ServiceRegistryFactoryBean.java
 * Modifier: xyang
 * Modified: 2012-09-26 22:12
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.framework.context.support.spring;

import org.springframework.beans.factory.FactoryBean;

import com.sklay.global.service.ServiceRegistry;
import com.sklay.global.service.ServiceRegistryFactory;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-9-26
 */
public class ServiceRegistryFactoryBean implements FactoryBean<ServiceRegistry> {
    @Override
    public ServiceRegistry getObject() throws Exception {
        return ServiceRegistryFactory.getInstance().getServiceRegistry();
    }

    @Override
    public Class<?> getObjectType() {
        return ServiceRegistry.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
