package com.sklay.service;

import java.util.List;
import java.util.Set;

import com.sklay.enums.DelStatus;
import com.sklay.model.News;

public interface NewsService {

	public News create(News news);

	public News update(News news);

	public News get(Long id);

	public List<News> get(Set<Long> ids);

	public void delete(Set<Long> ids, DelStatus delStatus);
}
