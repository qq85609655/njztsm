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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.dao.SpecificDao;
import com.sklay.dao.UserAttrDao;
import com.sklay.model.User;
import com.sklay.model.UserAttr;
import com.sklay.service.UserAttrService;

/**
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-6-27
 */
@Service
public class UserAttrServiceImpl implements UserAttrService {

	@Autowired
	private UserAttrDao userAttrDao;

	@Autowired
	private SpecificDao specificDao;

	@Override
	public void createUserAttr(Set<UserAttr> collection) throws SklayException {
		if (CollectionUtils.isNotEmpty(collection))
			userAttrDao.save(collection);
	}

	@Override
	public Page<User> getUserPage(Long group, String keyword, User user,
			Long belong, Pageable pageable) throws SklayException {
		if (null == pageable)
			throw new SklayException(ErrorCode.ILLEGAL_PARAM);

		return specificDao.findMemberPage(group, keyword, user, belong,
				pageable);
	}

	@Override
	public void removeAttrByUser(Long userId) throws SklayException {
		userAttrDao.removeAttrByUser(userId);
	}

	@Override
	public List<UserAttr> findUserAttrByKey(String key, Long userId) {
		return userAttrDao.findUserAttrByKey(key, userId);
	}

	@Override
	public void updateUserAttr(UserAttr userAttr) throws SklayException {
		userAttrDao.saveAndFlush(userAttr);
	}

	@Override
	public UserAttr createUserAttr(UserAttr userAttr) throws SklayException {
		if (null == userAttr)
			return null;
		userAttr.setId(null);
		return userAttrDao.saveAndFlush(userAttr);

	}

	@Override
	public List<UserAttr> findUserPaidAttrByKey(String key, Set<Long> userIds) {
		return userAttrDao.findUserPaidAttrByKey(key, userIds);
	}

}
