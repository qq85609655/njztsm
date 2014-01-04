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

import com.sklay.core.ex.SklayException;
import com.sklay.dao.SpecificGroupDao;
import com.sklay.model.Group;
import com.sklay.model.User;

@Repository
public class SpecificGroupDaoImpl implements SpecificGroupDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page<Group> page(String keyword, User owner, Long belong,
			Pageable pageable) throws SklayException {
		Query countQuery = initPage(keyword, owner, belong, true);
		Query dataQuery = initPage(keyword, owner, belong, false);
		Long total = (Long) countQuery.getSingleResult();
		if (!(total > 0))
			return null;
		dataQuery.setFirstResult(pageable.getOffset());
		dataQuery.setMaxResults(pageable.getPageSize());

		List<Group> resultList = dataQuery.getResultList();

		return new PageImpl(resultList, pageable, total);
	}

	private Query initPage(String keyword, User owner, Long belong,
			boolean count) {

		StringBuffer sb = null;
		if (count)
			sb = new StringBuffer("select count(g) from Group g where 1=1 ");
		else
			sb = new StringBuffer("select g from Group g where 1=1 ");

		if (null != belong) {
			if (null != owner) {
				sb.append(" and ( g.owner = :owner or g.belong = :belong ) ");
			} else
				sb.append(" and g.belong = :belong ");
		} else if (null != owner) {
			sb.append(" and g.owner = :owner ");
		}

		if (StringUtils.isNotBlank(keyword))
			sb.append(" and ( g.name like :keyword or g.description like :keyword ) ");

		Query query = em.createQuery(sb.toString());

		if (null != belong) {
			query.setParameter("belong", belong);
			if (null != owner)
				query.setParameter("owner", owner);
		} else if (null != owner) {
			query.setParameter("owner", owner);
		}

		if (StringUtils.isNotBlank(keyword))
			query.setParameter("keyword", "%"
					+ keyword.replaceAll("%", "").trim() + "%");

		return query;
	}

}
