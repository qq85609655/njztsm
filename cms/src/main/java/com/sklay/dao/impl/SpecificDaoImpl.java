package com.sklay.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.sklay.core.enums.MemberRole;
import com.sklay.dao.SpecificDao;
import com.sklay.model.User;
import com.sklay.model.Comment;

@Repository
public class SpecificDaoImpl implements SpecificDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAgent(User owner) {
		StringBuffer sb = new StringBuffer(
				"select u from User u , Group g where u.group.id=g.id and g.owner=:owner and g.role = :role ");
		Query query = em.createQuery(sb.toString());

		query.setParameter("owner", owner);
		query.setParameter("role", MemberRole.AGENT);

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getLatest(List<String> owners, boolean onlyImg,
			Date start, Date end, int offset, int limit) {
		StringBuffer hql = new StringBuffer("from Comment where 1=1 ");

		boolean ownersFlag = !CollectionUtils.isEmpty(owners);
		boolean startFlag = start != null;
		boolean endFlag = end != null;

		if (ownersFlag) {
			hql.append(" and owner in :owners");
		}
		if (onlyImg) {
			hql.append(" and imgCount>0");
		}
		if (startFlag) {
			hql.append(" and createAt>=:start");
		}
		if (endFlag) {
			hql.append(" and createAt<:end");
		}

		hql.append(" order by id desc");

		Query query = createHqlQuery(hql.toString(), offset, limit);

		if (ownersFlag) {
			query.setParameter("owners", owners);
		}
		if (startFlag) {
			query.setParameter("start", start);
		}
		if (endFlag) {
			query.setParameter("end", end);
		}

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getMostCommented(List<String> owners, boolean onlyImg,
			Date start, Date end, int offset, int limit) {
		StringBuffer hql = new StringBuffer("from Comment where 1=1 ");

		boolean ownersFlag = !CollectionUtils.isEmpty(owners);
		boolean startFlag = start != null;
		boolean endFlag = end != null;

		if (ownersFlag) {
			hql.append(" and owner in :owners");
		}
		if (onlyImg) {
			hql.append(" and imgCount>0");
		}
		if (startFlag) {
			hql.append(" and createAt>=:start");
		}
		if (endFlag) {
			hql.append(" and createAt<:end");
		}

		hql.append(" order by commented desc");

		Query query = createHqlQuery(hql.toString(), offset, limit);

		if (ownersFlag) {
			query.setParameter("owners", owners);
		}
		if (startFlag) {
			query.setParameter("start", start);
		}
		if (endFlag) {
			query.setParameter("end", end);
		}

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getMostViewed(List<String> owners, boolean onlyImg,
			Date start, Date end, int offset, int limit) {
		StringBuffer hql = new StringBuffer("from Comment where 1=1 ");

		boolean ownersFlag = !CollectionUtils.isEmpty(owners);
		boolean startFlag = start != null;
		boolean endFlag = end != null;

		if (ownersFlag) {
			hql.append(" and owner in :owners");
		}
		if (onlyImg) {
			hql.append(" and imgCount>0");
		}
		if (startFlag) {
			hql.append(" and createAt>=:start");
		}
		if (endFlag) {
			hql.append(" and createAt<:end");
		}

		hql.append(" order by viewed desc");

		Query query = createHqlQuery(hql.toString(), offset, limit);

		if (ownersFlag) {
			query.setParameter("owners", owners);
		}
		if (startFlag) {
			query.setParameter("start", start);
		}
		if (endFlag) {
			query.setParameter("end", end);
		}

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getMostLiked(List<String> owners, boolean onlyImg,
			Date start, Date end, int offset, int limit) {
		StringBuffer hql = new StringBuffer("from Comment where 1=1 ");

		boolean ownersFlag = !CollectionUtils.isEmpty(owners);
		boolean startFlag = start != null;
		boolean endFlag = end != null;

		if (ownersFlag) {
			hql.append(" and owner in :owners");
		}
		if (onlyImg) {
			hql.append(" and imgCount>0");
		}
		if (startFlag) {
			hql.append(" and createAt>=:start");
		}
		if (endFlag) {
			hql.append(" and createAt<:end");
		}

		hql.append(" order by liked desc");

		Query query = createHqlQuery(hql.toString(), offset, limit);

		if (ownersFlag) {
			query.setParameter("owners", owners);
		}
		if (startFlag) {
			query.setParameter("start", start);
		}
		if (endFlag) {
			query.setParameter("end", end);
		}

		return query.getResultList();
	}

	private Query createHqlQuery(String hql, int offset, int limit) {
		Query query = em.createQuery(hql.toString());
		if (offset < 0) {
			offset = 0;
		}
		query.setFirstResult(offset);
		if (limit > 0) {
			query.setMaxResults(limit);
		}
		return query;
	}
}
