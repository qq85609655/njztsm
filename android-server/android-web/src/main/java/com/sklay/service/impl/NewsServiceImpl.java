package com.sklay.service.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sklay.dao.NewsDao;
import com.sklay.enums.DelStatus;
import com.sklay.model.News;
import com.sklay.service.NewsService;

@Service
public class NewsServiceImpl implements NewsService {

	@Autowired
	private NewsDao newsDao;

	@Override
	public News create(News news) {
		if (null == news)
			return null;
		return newsDao.save(news);
	}

	@Override
	public News get(Long id) {
		if (null == id)
			return null;
		return newsDao.findOne(id);
	}

	@Override
	public News update(News news) {
		if (null == news)
			return null;
		return newsDao.save(news);
	}

	@Override
	public List<News> get(Set<Long> ids) {
		if (CollectionUtils.isEmpty(ids))
			return null;

		return newsDao.get(ids);
	}

	@Override
	public void delete(Set<Long> ids, DelStatus delStatus) {
		newsDao.delete(ids, delStatus);
	}
}
