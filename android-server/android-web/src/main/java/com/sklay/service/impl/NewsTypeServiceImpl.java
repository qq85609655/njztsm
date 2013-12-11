package com.sklay.service.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sklay.dao.NewsTypeDao;
import com.sklay.service.NewsTypeService;
import com.sklay.model.NewsType;

@Service
public class NewsTypeServiceImpl implements NewsTypeService {

	@Autowired
	private NewsTypeDao newsTypeDao;

	@Override
	public NewsType cerate(NewsType newsType) {
		if (null == newsType)
			return null;

		return newsTypeDao.save(newsType);
	}

	@Override
	public List<NewsType> cerate(Set<NewsType> newsTypes) {
		if (CollectionUtils.isEmpty(newsTypes))
			return null;

		return newsTypeDao.save(newsTypes);
	}

	@Override
	public List<NewsType> list() {
		return newsTypeDao.findAll(new Sort(Sort.Direction.DESC, "id"));
	}

	@Override
	public NewsType get(Long typeId) {
		return newsTypeDao.findOne(typeId);
	}
}
