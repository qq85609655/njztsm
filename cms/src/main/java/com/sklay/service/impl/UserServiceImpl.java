/*
 * Project:  sklay
 * Module:   sklay-web
 * File:     UserServiceImpl.java
 * Modifier: fuyu
 * Modified: 2012-12-14 下午3:35:16 
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.service.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sklay.core.ex.SklayException;
import com.sklay.core.util.PwdUtils;
import com.sklay.dao.UserDao;
import com.sklay.model.User;
import com.sklay.service.UserService;

/**
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-6-27
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public void regist(User user) throws SklayException {
		cleanBeforeRegist(user);
		// the same as shiro.xml
		user.setPassword(PwdUtils.MD256Pws(user.getPassword()));
		userDao.save(user);
	}

	@Override
	public User create(User user) throws SklayException {
		cleanBeforeRegist(user);
		return userDao.save(user);
	}

	private void cleanBeforeRegist(User user) throws SklayException {
		user.setId(null);
		user.setSalt(null);
	}

	@Override
	public boolean exist(String phone) throws SklayException {

		if (StringUtils.isBlank(phone))
			return false;

		return userDao.countByPhone(phone.trim()) > 0;
	}

	@Override
	public User getUser(Long userId) throws SklayException {

		if (userId == null) {
			return null;
		}

		return userDao.findOne(userId);
	}

	@Override
	public List<User> getUser(Set<Long> userId) throws SklayException {

		if (CollectionUtils.isEmpty(userId)) {
			return null;
		}

		return (List<User>) userDao.findAll(userId);
	}

	public void update(User user) throws SklayException {
		if (null != user)
			userDao.saveAndFlush(user);
	}

	@Override
	public void delete(User user) throws SklayException {
		userDao.delete(user);
	}

	@Override
	public void delete(Long userId) throws SklayException {
		userDao.delete(userId);
	}

}
