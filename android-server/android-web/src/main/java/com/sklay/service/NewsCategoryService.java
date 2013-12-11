package com.sklay.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sklay.enums.AuditStatus;
import com.sklay.model.News;
import com.sklay.model.NewsCategory;
import com.sklay.model.NewsCategoryPK;

public interface NewsCategoryService {

	public Page<News> newsPage(String keyword, Pageable pageable);

	public Page<NewsCategory> newsCategorypage(String keyword, Long typeId,
			AuditStatus status, Pageable pageable);

	public List<NewsCategory> getByNewsIds(Set<Long> ids);

	public NewsCategory create(NewsCategory category);

	public NewsCategory get(Long newsId, Long typeId);

	public List<NewsCategory> getByNewsId(Long newsId);

	public List<NewsCategory> create(Set<NewsCategory> categories);

	public NewsCategory update(NewsCategory category);

	public void delete(NewsCategory category);

	public void delete(Set<NewsCategory> categories);

	public void deleteByPK(Set<NewsCategoryPK> categories);

	public void audit(Set<NewsCategoryPK> categories, AuditStatus status);

}
