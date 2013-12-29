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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sklay.core.ex.SklayException;
import com.sklay.model.User;
import com.sklay.model.UserAttr;

/**
 * 
 * .
 * <p/>
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-7-15
 */
public interface UserAttrService {

	void createUserAttr(Set<UserAttr> collection) throws SklayException;

	void updateUserAttr(UserAttr userAttr) throws SklayException;

	UserAttr createUserAttr(UserAttr userAttr) throws SklayException;

	void removeAttrByUser(Long userId) throws SklayException;

	public List<UserAttr> findUserAttrByKey(String key, Long userId);

	public List<UserAttr> findUserPaidAttrByKey(String key, Set<Long> userIds);

	public Page<User> getUserPage(Long group, String keyword, User user,
			Long belong, Pageable pageable) throws SklayException;

}
