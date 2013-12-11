/*
 * @(#)FileOperation.java 2011
 *
 * Copyright 2011 Sklay SoftWare, Inc. All rights reserved.
 * SKLAY Limited Company/CONFIDENTIAL. Use is subject to license terms.
 */
package com.sklay.core.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 
 * .
 * <p/>
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-8-28
 */
public interface FileOperation {
	String SCHEME_HTTP = "http";

	String SCHEME_HTTPS = "https";

	void save(String destination) throws IOException;

	void save(File file) throws IOException;

	void send(OutputStream outputStream) throws IOException;
}
