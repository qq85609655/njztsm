/*
 * Project:  sklay
 * Module:   sklay-core
 * File:     SqlScriptExecutor.java
 * Modifier: fuyu
 * Modified: 2012-12-8 下午5:06:35 
 *
 * Copyright (c) 2012 Sklay Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.core.database;


import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 * 
 *  .
 * <p/>
 *
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-7-9
 */
public abstract class AbstractSqlScriptExecutor implements InitializingBean{
	
	private ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
	
	private Resource[] scripts;
	
	private DataSource dataSource;
	
	private String charset = "UTF-8";
	
	public void setScripts(Resource[] scripts) {
		this.scripts = scripts;
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void setCharset(String charset) {
		this.charset = charset;
	}

	public abstract boolean canExecute();

	@Override
	public void afterPropertiesSet() throws Exception {
		if(canExecute()){
			populator.setSqlScriptEncoding(charset);
			populator.setIgnoreFailedDrops(false);
			populator.setContinueOnError(false);
			for (Resource script : scripts) {
				populator.addScript(script);
			}
			DatabasePopulatorUtils.execute(populator, dataSource);
		}
	}
}