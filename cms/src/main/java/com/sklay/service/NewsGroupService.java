package com.sklay.service;

import java.util.List;

import com.sklay.model.NewsGroup;

public interface NewsGroupService {

	void save(NewsGroup group);

	void delete(Long groupId);

	List<NewsGroup> getRootGroups(String biz, String owner);

	NewsGroup findOne(Long groupId);

	List<NewsGroup> getPathGroups(Long groupId);

	NewsGroup getFirstLeaf(String biz, String owner);

	List<NewsGroup> findChildLeavesByPath(String path);

	List<NewsGroup> getChildren(Long pid);

	List<NewsGroup> findAll();

	List<NewsGroup> findAllLeaves(String biz, String owner);

	List<NewsGroup> findByIdIn(List<Long> groupIds);

}
