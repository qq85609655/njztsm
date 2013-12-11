/*
 * Project:  any-parent
 * Module:   any-framework
 * File:     NLS.java
 * Modifier: ozn
 * Modified: 2012-08-13 19:06
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.core.message;

import java.text.MessageFormat;
import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * 
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-7-2
 */
public final class NLS {
	private static MessageSource messageSource;

	public void setMessageSource(MessageSource messageSource) {
		NLS.messageSource = messageSource;
	}

	public static String getMessage(String key) {
		return getMessage(key, null, null, null);
	}

	public static String getMessage(String key, Object[] args) {
		return getMessage(key, args, null, null);
	}

	public static String getMessage(String key, Object[] args,
			String defaultMessage) {
		return getMessage(key, args, defaultMessage, null);
	}

	public static String getMessage(String key, Object[] args,
			String defaultMessage, Locale locale) {
		return messageSource.getMessage(key, args, defaultMessage, locale);
	}

	public static String getMsg(String pattern, Object[] formatArgs) {
		return MessageFormat.format(pattern, formatArgs);
	}
}
