package com.sklay.service;

import java.util.List;
import java.util.Set;

import com.sklay.model.NewsType;

public interface NewsTypeService {

	public NewsType cerate(NewsType newsType);

	public List<NewsType> cerate(Set<NewsType> newsTypes);

	public List<NewsType> list();

	public NewsType get(Long typeId);
}
