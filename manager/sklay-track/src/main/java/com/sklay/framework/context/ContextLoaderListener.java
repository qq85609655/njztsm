/*
 * Project:  any
 * Module:   any-framework
 * File:     AnyContextLoader.java
 * Modifier: xyang
 * Modified: 2012-09-22 19:26
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.framework.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.sklay.core.util.Constants;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * .
 * <p/>
 * 
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-9-22
 */
public class ContextLoaderListener extends ContextLoader implements
		ServletContextListener {
	private static Logger logger = LoggerFactory
			.getLogger(ContextLoaderListener.class);

	public static final String PROFILE_ACTIVE = "profile.active";
	public static final String PROFILE_CONFIG_LOCATION_PARAM = "profileConfigFileLocation";
	public static final String PROFILE_DEV = "dev";
	public static final String PROFILE_TEST = "test";
	public static final String PROFILE_PRODUCTION = "production";
	public static final String FILE_LOG = "filelog.enable";

	@Override
	public void contextInitialized(ServletContextEvent event) {
		initWebApplicationContext(event.getServletContext());
	}

	@Override
	public WebApplicationContext initWebApplicationContext(
			ServletContext servletContext) {
		GlobalConfigLoader.load();
		String activeProfile = System.getProperty(PROFILE_ACTIVE);
		if (activeProfile == null) {
			activeProfile = loadFromApplicationProperties(servletContext);
			if (activeProfile == null) {
				activeProfile = System
						.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME);
				if (activeProfile == null) {
					activeProfile = servletContext
							.getInitParameter(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME);
					if (activeProfile == null) {
						activeProfile = PROFILE_DEV;
					}
				}
			}
		}
		if ("${profile.active}".equals(activeProfile)) {
			activeProfile = PROFILE_DEV;
		}
		System.setProperty(PROFILE_ACTIVE, activeProfile);
		System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME,
				activeProfile);

		// init log
		if (System.getProperty(FILE_LOG) == null
				&& activeProfile.contains(PROFILE_PRODUCTION)) {
			System.setProperty(FILE_LOG, "true");
		}
		if (!"true".equals(System.getProperty(FILE_LOG))) {
			System.clearProperty(FILE_LOG);
		}

		SLF4JBridgeHandler.install(); // 替换jdk log

		return super.initWebApplicationContext(servletContext);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		try {
			closeWebApplicationContext(event.getServletContext());
		} finally {
			SLF4JBridgeHandler.uninstall();
			cleanup(event.getServletContext());
		}
	}

	@Override
	protected void customizeContext(ServletContext servletContext,
			ConfigurableWebApplicationContext applicationContext) {
		super.customizeContext(servletContext, applicationContext);
	}

	private String loadFromApplicationProperties(ServletContext sc) {
		String location = sc.getInitParameter("profileConfigFileLocation");
		if (location == null) {
			location = "classpath:/application.properties";
		}
		InputStream is = null;
		try {
			is = ResourceUtils.getURL(location).openStream();
			Properties props = new Properties();
			props.load(is);
			return (String) props.get(Constants.ANY_PROFILE_ACTIVE);
		} catch (Exception ignored) {
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ignored) {
				}
			}
		}
		return null;
	}

	private void cleanup(ServletContext sc) {
		cleanupAttributes(sc);
		CachedIntrospectionResults.clearClassLoader(Thread.currentThread()
				.getContextClassLoader());
	}

	private void cleanupAttributes(ServletContext sc) {
		Enumeration attrNames = sc.getAttributeNames();
		while (attrNames.hasMoreElements()) {
			String attrName = (String) attrNames.nextElement();
			if (attrName.startsWith("org.springframework.")) {
				Object attrValue = sc.getAttribute(attrName);
				if (attrValue instanceof DisposableBean) {
					try {
						((DisposableBean) attrValue).destroy();
					} catch (Throwable ex) {
						logger.error(
								"Couldn't invoke destroy method of attribute with name '"
										+ attrName + "'", ex);
					}
				}
			}
		}
	}
}
