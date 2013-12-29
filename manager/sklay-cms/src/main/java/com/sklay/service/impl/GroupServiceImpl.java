package com.sklay.service.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sklay.core.enums.AuditStatus;
import com.sklay.core.enums.MemberRole;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.core.util.Constants;
import com.sklay.dao.GroupDao;
import com.sklay.dao.SpecificGroupDao;
import com.sklay.model.Group;
import com.sklay.model.User;
import com.sklay.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private SpecificGroupDao specificGroupDao;

	@Override
	public Group create(Group group) throws SklayException {
		if (null == group)
			throw new SklayException(ErrorCode.ILLEGAL_PARAM);
		group.setId(null);
		return groupDao.save(group);
	}

	@Override
	public Group get(Long groupId) throws SklayException {
		if (null == groupId)
			throw new SklayException(ErrorCode.ILLEGAL_PARAM);
		return groupDao.findOne(groupId);
	}

	@Override
	public List<Group> getGroupAll() throws SklayException {
		return groupDao.findAll();
	}

	@Override
	public List<Group> getGroupAll(Set<Long> ids) throws SklayException {
		return (List<Group>) groupDao.findAll(ids);
	}

	@Override
	public Page<Group> getGroupPage(String keyword, User owner, Long belong,
			Pageable pageable) throws SklayException {
		return specificGroupDao.page(keyword, owner, belong, pageable);

	}

	@Override
	public void edit(Group group) throws SklayException {
		if (null == group)
			throw new SklayException(ErrorCode.ILLEGAL_PARAM);
		groupDao.save(group);
	}

	@Override
	public void delete(Long groupId) throws SklayException {
		if (null == groupId)
			throw new SklayException(ErrorCode.ILLEGAL_PARAM);
		groupDao.delete(groupId);
	}

	@Override
	public void create(Set<Group> group) throws SklayException {
		if (CollectionUtils.isEmpty(group))
			throw new SklayException(ErrorCode.ILLEGAL_PARAM);
		groupDao.save(group);
	}

	@Override
	public List<Group> getGroupByOwner(User owner) throws SklayException {
		if (null == owner)
			throw new SklayException(ErrorCode.ILLEGAL_PARAM);
		return groupDao.findGroupByOwner(owner);
	}

	@Override
	@CacheEvict(value = Constants.SHIRO_AUTHOR_ACAHE, allEntries = true)
	public void update(Group group) throws SklayException {
		groupDao.saveAndFlush(group);

	}

	@Override
	public void update(Set<Group> groups) throws SklayException {
		groupDao.save(groups);
	}

	@Override
	public void deleteByUser(User user) throws SklayException {
		groupDao.deleteByUser(user);
	}

	@Override
	public Group getSuperAdminGroup() throws SklayException {

		return groupDao.findSuperAdminGroup(AuditStatus.PASS,
				MemberRole.ADMINSTROTAR);
	}

	@Override
	public List<Group> getGroupByParentId(Set<Long> parentId)
			throws SklayException {
		if (CollectionUtils.isNotEmpty(parentId))
			return groupDao.findGroupByParentId(parentId);
		return null;
	}

	@Override
	public List<Group> getBelongGroup(Long belong) throws SklayException {
		return groupDao.getBelongGroup(belong);
	}

}
