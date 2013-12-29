package com.sklay.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sklay.core.ex.SklayException;
import com.sklay.model.Group;
import com.sklay.model.User;

public interface GroupService {

	public Group create(Group group) throws SklayException;

	public void update(Group group) throws SklayException;

	public void update(Set<Group> groups) throws SklayException;

	public void create(Set<Group> group) throws SklayException;

	public Group get(Long groupId) throws SklayException;

	public Group getSuperAdminGroup() throws SklayException;

	public List<Group> getGroupAll() throws SklayException;

	public List<Group> getGroupAll(Set<Long> ids) throws SklayException;

	public List<Group> getGroupByOwner(User owner) throws SklayException;

	public List<Group> getGroupByParentId(Set<Long> parentId)
			throws SklayException;

	public List<Group> getBelongGroup(Long belong) throws SklayException;

	public Page<Group> getGroupPage(String keyword, User owner, Long belong,
			Pageable pageable) throws SklayException;

	public void edit(Group group) throws SklayException;

	public void delete(Long groupId) throws SklayException;

	public void deleteByUser(User user) throws SklayException;
}
