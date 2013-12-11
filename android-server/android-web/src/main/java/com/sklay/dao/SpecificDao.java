package com.sklay.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sklay.enums.AuditStatus;
import com.sklay.model.News;
import com.sklay.model.NewsCategory;

public interface SpecificDao {

	public Page<News> newsPage(String keyword, Pageable pageable);

	public Page<NewsCategory> newsCategorypage(String keyword, Long typeId,
			AuditStatus status, Pageable pageable);
}
