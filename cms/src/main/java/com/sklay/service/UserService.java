/*
 * Project:  sklay
 * Module:   sklay-web
 * File:     UserService.java
 * Modifier: zhouning
 * Modified: 2012-12-8 下午3:43:38 
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.service;

import java.util.List;
import java.util.Set;

import com.sklay.core.ex.SklayException;
import com.sklay.model.User;

/**
 * 
 * .
 * <p/>
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-7-15
 */
public interface UserService {

	/**
	 * @param user
	 */
	void regist(User user) throws SklayException;

	User create(User user) throws SklayException;

	void update(User user) throws SklayException;

	void delete(User user) throws SklayException;

	void delete(Long userId) throws SklayException;

	boolean exist(String phone) throws SklayException;

	User getUser(Long userId) throws SklayException;

	List<User> getUser(Set<Long> userId) throws SklayException;

}
