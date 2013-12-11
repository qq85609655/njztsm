package com.sklay.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sklay.model.NewsGroup;
import com.sklay.service.NewsGroupService;
import com.sklay.dao.NewsGroupDao;

@Service
public class NewsGroupServiceImpl implements NewsGroupService {

	@Autowired
	private NewsGroupDao groupDao;

	@Override
	public void save(NewsGroup group) {
		Long pid = group.getPid();
		String pathPrefix = "|";
		if (pid != null) {
			NewsGroup p = groupDao.findOne(pid);
			if (p != null) {
				pathPrefix = p.getPath();
				p.setLeaf(false);
			}
		}
		groupDao.save(group);
		group.setPath(pathPrefix + group.getId() + "|");
		groupDao.save(group);
	}

	@Override
	public void delete(Long groupId) {
		NewsGroup group = groupDao.findOne(groupId);
		if (group == null) {
			return;
		}
		Long pid = group.getPid();
		groupDao.deleteByPathLike(group.getPath() + "%");

		if (pid != 0) {
			NewsGroup pgroup = groupDao.findOne(pid);
			if (pgroup != null) {
				List<NewsGroup> children = groupDao.findByPid(pid);
				if (CollectionUtils.isEmpty(children)) {
					pgroup.setLeaf(true);
					groupDao.save(pgroup);
				}
			}
		}
	}

	@Override
	public List<NewsGroup> getRootGroups(String biz, String owner) {
		return groupDao.findByBizAndOwnerAndPidAndLeafFalse(biz, owner, 0L);
	}

	@Override
	public NewsGroup findOne(Long groupId) {
		return groupDao.findOne(groupId);
	}

	@Override
	public List<NewsGroup> getPathGroups(Long groupId) {
		NewsGroup last = groupDao.findOne(groupId);
		if (last == null) {
			return Collections.emptyList();
		} else {
			String path = last.getPath();
			String[] paths = org.apache.commons.lang.StringUtils.split(path,
					"|");
			List<NewsGroup> pathGroups = null;
			if (paths.length > 1) {
				pathGroups = new ArrayList<NewsGroup>();
				for (int i = 0; i < paths.length - 1; i++) {
					pathGroups.add(groupDao.findOne(Long.parseLong(paths[i])));
				}
			} else {
				pathGroups = Collections.singletonList(last);
			}
			return pathGroups;
		}
	}

	@Override
	public NewsGroup getFirstLeaf(String biz, String owner) {
		return groupDao.findOneByBizAndOwnerAndLeafTrue(biz, owner);
	}

	@Override
	public List<NewsGroup> findChildLeavesByPath(String path) {
		return groupDao.findByLeafTrueAndPathStartingWith(path);
	}

	@Override
	public List<NewsGroup> getChildren(Long pid) {
		return groupDao.findByPid(pid);
	}

	@Override
	public List<NewsGroup> findAll() {
		return groupDao.findAll();
	}

	@Override
	public List<NewsGroup> findAllLeaves(String biz, String owner) {
		return groupDao.findByBizAndOwnerAndLeafTrue(biz, owner);
	}

	@Override
	public List<NewsGroup> findByIdIn(List<Long> groupIds) {
		return groupDao.findByIdIn(groupIds);
	}
}
