package com.sklay.service.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sklay.dao.NewsCategoryDao;
import com.sklay.dao.SpecificDao;
import com.sklay.enums.AuditStatus;
import com.sklay.model.News;
import com.sklay.model.NewsCategory;
import com.sklay.model.NewsCategoryPK;
import com.sklay.service.NewsCategoryService;

@Service
public class NewsCategoryServiceImpl implements NewsCategoryService {

	@Autowired
	private NewsCategoryDao newsCategoryDao;

	@Autowired
	private SpecificDao specificDao;

	@Override
	public Page<News> newsPage(String keyword, Pageable pageable) {
		return specificDao.newsPage(keyword, pageable);
	}

	@Override
	public Page<NewsCategory> newsCategorypage(String keyword, Long typeId,
			AuditStatus status, Pageable pageable) {
		return specificDao.newsCategorypage(keyword, typeId, status, pageable);
	}

	@Override
	public NewsCategory create(NewsCategory category) {
		if (null == newsCategoryDao)
			return null;

		return newsCategoryDao.save(category);
	}

	@Override
	public List<NewsCategory> create(Set<NewsCategory> categories) {
		if (CollectionUtils.isEmpty(categories))
			return null;
		return newsCategoryDao.save(categories);
	}

	@Override
	public void delete(NewsCategory category) {
		if (null != newsCategoryDao)
			newsCategoryDao.delete(category);
	}

	@Override
	public void delete(Set<NewsCategory> categories) {
		if (CollectionUtils.isNotEmpty(categories))
			newsCategoryDao.delete(categories);

	}

	@Override
	public List<NewsCategory> getByNewsIds(Set<Long> ids) {
		return newsCategoryDao.getByNewsIds(ids);
	}

	@Override
	public NewsCategory get(Long newsId, Long typeId) {
		return newsCategoryDao.get(newsId, typeId);
	}

	@Override
	public List<NewsCategory> getByNewsId(Long newsId) {
		return newsCategoryDao.getByNewsId(newsId);
	}

	@Override
	public NewsCategory update(NewsCategory category) {
		return newsCategoryDao.save(category);
	}

	@Override
	public void deleteByPK(Set<NewsCategoryPK> categories) {
		if (CollectionUtils.isNotEmpty(categories))
			for (NewsCategoryPK id : categories)
				newsCategoryDao.delete(id);
	}

	@Override
	public void audit(Set<NewsCategoryPK> categories, AuditStatus status) {
		newsCategoryDao.audit(status, categories);
	}

}
