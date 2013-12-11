package com.sklay.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.sklay.dao.SpecificDao;
import com.sklay.enums.AuditStatus;
import com.sklay.model.News;
import com.sklay.model.NewsCategory;

@Repository
public class SpecificDaoImpl implements SpecificDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page<News> newsPage(String keyword, Pageable pageable) {

		Query countQuery = initNewsPageQuery(keyword, true);
		Query dataQuery = initNewsPageQuery(keyword, false);

		Long total = (Long) countQuery.getSingleResult();
		if (!(total > 0))
			return null;
		dataQuery.setFirstResult(pageable.getOffset());
		dataQuery.setMaxResults(pageable.getPageSize());
		List<News> resultList = dataQuery.getResultList();

		return new PageImpl(resultList, pageable, total);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page<NewsCategory> newsCategorypage(String keyword, Long typeId,
			AuditStatus status, Pageable pageable) {

		Query countQuery = initNewsCategoryPageQuery(keyword, typeId, status,
				true);
		Query dataQuery = initNewsCategoryPageQuery(keyword, typeId, status,
				false);

		Long total = (Long) countQuery.getSingleResult();
		if (!(total > 0))
			return null;
		dataQuery.setFirstResult(pageable.getOffset());
		dataQuery.setMaxResults(pageable.getPageSize());
		List<NewsCategory> resultList = dataQuery.getResultList();

		return new PageImpl(resultList, pageable, total);
	}

	private Query initNewsPageQuery(String keyword, boolean isCount) {

		StringBuffer qlString = new StringBuffer(" select ");
		if (isCount)
			qlString.append(" count( DISTINCT n) ");
		else
			qlString.append(" DISTINCT n ");

		qlString.append(" from News n  where n.delStatus = 0 ");

		if (StringUtils.isNotBlank(keyword))
			qlString.append(" and  n.title like :keyword  ");

		qlString.append(" order by  n.id desc ");

		Query query = em.createQuery(qlString.toString());

		if (StringUtils.isNotBlank(keyword))
			query.setParameter("keyword", "%" + keyword + "%");

		return query;
	}

	private Query initNewsCategoryPageQuery(String keyword, Long typeId,
			AuditStatus status, boolean isCount) {

		StringBuffer qlString = new StringBuffer(" select ");
		if (isCount)
			qlString.append(" count( DISTINCT nc) ");
		else
			qlString.append(" DISTINCT nc ");

		qlString.append(" from NewsCategory nc where 1 = 1 ");

		if (StringUtils.isNotBlank(keyword))
			qlString.append(" and nc.newsCategory.news.title like :keyword  ");

		if (null != typeId)
			qlString.append(" and nc.newsCategory.newsType.id = :typeId  ");

		if (null != status)
			qlString.append(" and nc.status = :status  ");

		qlString.append(" order by  nc.createTime desc ");

		Query query = em.createQuery(qlString.toString());

		if (StringUtils.isNotBlank(keyword))
			query.setParameter("keyword", "%" + keyword + "%");

		if (null != typeId)
			query.setParameter("typeId", typeId);

		if (null != status)
			query.setParameter("status", status);

		return query;
	}
}
