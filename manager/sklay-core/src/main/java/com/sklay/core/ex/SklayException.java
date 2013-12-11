/*
 * Project:  any
 * Module:   any-framework
 * File:     AnyException.java
 * Modifier: xyang
 * Modified: 2012-07-04 09:25
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.core.ex;

/**
 * 
 * .
 * <p/>
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-7-2
 */
public class SklayException extends RuntimeException implements ErrorCode {
	private static final long serialVersionUID = 4595549517180869921L;
	private int code = SERVER_ERROR;
	private Object args[];

	private boolean logStackTrace = true;

	private String renderedMessage;

	public SklayException() {
	}

	public SklayException(String message) {
		super(message);
	}

	public SklayException(String message, Throwable cause) {
		super(message, cause);
	}

	public SklayException(Throwable cause) {
		super(cause);
	}

	public SklayException(int code) {
		super();
		this.code = code;
	}

	public SklayException(int code, Throwable cause) {
		this(cause);
		this.code = code;
	}

	public SklayException(int code, String defaultMessage, Object... args) {
		super(defaultMessage);
		this.code = code;
		this.args = args;
	}

	public SklayException(int code, String defaultMessage, Throwable cause,
			Object... args) {
		super(defaultMessage, cause);
		this.code = code;
		this.args = args;
	}

	public int getCode() {
		return code;
	}

	public Object[] getArgs() {
		return args;
	}

	@Override
	public String getMessage() {
		if (renderedMessage == null) {
			renderedMessage = ExceptionUtils.buildMessage(code, args,
					super.getMessage(), getCause());
		}
		return renderedMessage;
	}

	@Override
	public String toString() {
		return getMessage();
	}

	public static SklayException fromRoot(Exception e) {
		return new SklayException(ExceptionUtils.getRootCause(e));
	}

	public void setRenderedMessage(String renderedMessage) {
		this.renderedMessage = renderedMessage;
	}

	public boolean isLogStackTrace() {
		return logStackTrace;
	}

	public void setLogStackTrace(boolean logStackTrace) {
		this.logStackTrace = logStackTrace;
	}

	public SklayException loggable(boolean loggable) {
		this.setLogStackTrace(loggable);
		return this;
	}

}
