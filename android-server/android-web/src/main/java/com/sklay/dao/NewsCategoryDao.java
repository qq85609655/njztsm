package com.sklay.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sklay.enums.AuditStatus;
import com.sklay.model.NewsCategory;
import com.sklay.model.NewsCategoryPK;

public interface NewsCategoryDao extends
		JpaRepository<NewsCategory, NewsCategoryPK>,
		JpaSpecificationExecutor<NewsCategoryPK> {

	@Query("select n from NewsCategory n where n.newsCategory.news.id in (?1) ")
	public List<NewsCategory> getByNewsIds(Set<Long> ids);

	@Query("select n from NewsCategory n where n.newsCategory.news.id = ?1 and n.newsCategory.newsType.id = ?2  ")
	public NewsCategory get(Long newsId, Long typeId);

	@Query("select n from NewsCategory n where n.newsCategory.news.id = ?1")
	public List<NewsCategory> getByNewsId(Long newsId);

	@Modifying
	@Query("update NewsCategory n set n.status = ?1  where n.newsCategory in (?2) ")
	public void audit(AuditStatus status, Set<NewsCategoryPK> pks);
}
