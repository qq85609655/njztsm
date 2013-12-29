package com.sklay.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.sklay.core.enums.AppType;
import com.sklay.core.enums.AuditStatus;
import com.sklay.core.enums.BindingMold;
import com.sklay.core.enums.Level;
import com.sklay.core.enums.MemberRole;
import com.sklay.core.enums.ReadType;
import com.sklay.core.enums.SMSStatus;
import com.sklay.core.enums.SMSType;
import com.sklay.core.enums.Sex;
import com.sklay.core.enums.TipType;
import com.sklay.core.ex.SklayException;
import com.sklay.dao.SpecificDao;
import com.sklay.enums.LogLevelType;
import com.sklay.model.Application;
import com.sklay.model.DeviceBinding;
import com.sklay.model.GlobalSetting;
import com.sklay.model.Group;
import com.sklay.model.MataData;
import com.sklay.model.MedicalReport;
import com.sklay.model.Operation;
import com.sklay.model.SMS;
import com.sklay.model.User;

@Repository
public class SpecificDaoImpl implements SpecificDao {

	@PersistenceContext
	private EntityManager em;

	private final static int ZERO = 0;

	@SuppressWarnings("unchecked")
	public List<MataData> queryUserMataData(int age, int sex, int lowPressure,
			int highPressure) {
		StringBuffer sb = new StringBuffer(
				"select m from MataData m where m.ageMin<= :age and  m.ageMax >= :age and m.sex = :sex and m.lowPressureMin <= :lowPressure and m.lowPressureMax >= :lowPressure and m.highPressureMin <= :highPressure and m.highPressureMax >= :lowPressure order by m.ageMin asc ");

		Query query = em.createQuery(sb.toString());
		query.setParameter("age", age);
		query.setParameter("sex", sex);
		query.setParameter("lowPressure", lowPressure);
		query.setParameter("highPressure", highPressure);

		List<MataData> list = query.getResultList();

		return list;
	}

	@Override
	public MedicalReport createMedicalReport(MedicalReport report) {
		return null;

	}

	@SuppressWarnings("unchecked")
	@Override
	public GlobalSetting getGlobalConfig() {
		StringBuffer sb = new StringBuffer("select g from GlobalSetting g");
		Query query = em.createQuery(sb.toString());
		List<GlobalSetting> list = query.getResultList();

		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DeviceBinding getDefaultBinding(String sn) throws SklayException {
		StringBuffer sb = new StringBuffer(
				" select b from DeviceBinding b where b.serialNumber = :sn and b.level = :level order by b.targetUser asc");

		Query query = em.createQuery(sb.toString());

		query.setParameter("sn", sn);
		query.setParameter("level", Level.FIRST);

		List<DeviceBinding> list = query.getResultList();

		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(ZERO);
	}

	@SuppressWarnings("unchecked")
	public List<DeviceBinding> getDefaultBindings(Set<String> devices,
			Level level) throws SklayException {
		StringBuffer sb = new StringBuffer(
				" select b from DeviceBinding b where b.serialNumber in ( :sn ) and b.level = :level order by b.targetUser asc");

		Query query = em.createQuery(sb.toString());

		query.setParameter("sn", devices);
		query.setParameter("level", Level.FIRST);

		List<DeviceBinding> list = query.getResultList();

		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getBindingUser(String sn, Level level,
			AuditStatus auditStatus, BindingMold mold) throws SklayException {
		StringBuffer sb = new StringBuffer(
				" select u from User u , DeviceBinding b where u.id = b.targetUser and  b.serialNumber = :sn ");

		if (null != level) {
			sb.append(" and b.level = :level ");
		}

		if (null != auditStatus) {
			sb.append(" and b.status = :status ");
		}

		if (null != mold)
			sb.append(" and b.mold = :mold ");

		sb.append("  order by b.level asc ");
		Query query = em.createQuery(sb.toString());

		query.setParameter("sn", sn);

		if (null != level) {
			query.setParameter("level", level);
		}

		if (null != auditStatus)
			query.setParameter("status", auditStatus);

		if (null != mold)
			query.setParameter("mold", mold);

		return query.getResultList();
	}

	@Override
	public long getBindingUserCount(String sn, Level level, BindingMold mold,
			AuditStatus auditStatus) throws SklayException {

		StringBuffer sb = new StringBuffer(
				" select count (u) from User u , DeviceBinding b where u.id = b.targetUser and  b.serialNumber = :sn ");

		if (null != level) {
			sb.append(" and b.level = :level ");
		}

		if (null != auditStatus) {
			sb.append(" and b.status = :status ");
		}

		if (null != mold) {
			sb.append(" and b.mold = :mold ");
		}

		sb.append("  order by b.level asc ");
		Query query = em.createQuery(sb.toString());

		query.setParameter("sn", sn);

		if (null != level) {
			query.setParameter("level", level);
		}

		if (null != auditStatus)
			query.setParameter("status", auditStatus);

		if (null != mold)
			query.setParameter("mold", mold);

		return (Long) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeviceBinding> getUserBinding(Set<Long> targetUser,
			Level level, User creator) {
		StringBuffer sb = new StringBuffer(
				" select b from DeviceBinding b where 1=1 ");

		if (CollectionUtils.isNotEmpty(targetUser))
			sb.append(" and b.targetUser.id in ( :targetUser ) ");
		if (null != level)
			sb.append(" and b.level = :level ");
		if (null != creator)
			sb.append(" and b.creator = :creator ");

		Query query = em.createQuery(sb.toString());
		if (CollectionUtils.isNotEmpty(targetUser))
			query.setParameter("targetUser", targetUser);
		if (null != level)
			query.setParameter("level", level);
		if (null != creator)
			query.setParameter("creator", creator);
		return query.getResultList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page<MataData> getMataDataPage(Integer minAge, Integer maxAge,
			Sex sex, Integer lowPressure, Integer highPressure, String keyword,
			Pageable pageable) throws SklayException {

		Query countQuery = initMataDataSearch(minAge, maxAge, sex, lowPressure,
				highPressure, keyword, true);
		Query dataQuery = initMataDataSearch(minAge, maxAge, sex, lowPressure,
				highPressure, keyword, false);

		Long total = (Long) countQuery.getSingleResult();
		if (!(total > 0))
			return null;
		dataQuery.setFirstResult(pageable.getOffset());
		dataQuery.setMaxResults(pageable.getPageSize());
		List<MataData> resultList = dataQuery.getResultList();

		return new PageImpl(resultList, pageable, total);
	}

	@SuppressWarnings("unchecked")
	@Override
	public MedicalReport findLastReport(Long userId, SMSType reportType) {
		Query query = em
				.createQuery("select m from MedicalReport m where m.targetUser.id = :userId and  m.reportType = :reportType order by m.reportTime desc");

		query.setParameter("userId", userId);
		query.setParameter("reportType", reportType);

		query.setFirstResult(0);
		query.setMaxResults(1);
		List<MedicalReport> resultList = query.getResultList();

		return CollectionUtils.isEmpty(resultList) ? null : resultList.get(0);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page<MedicalReport> searchMedicalReport(String keyword,
			TipType tipType, ReadType readType, User user, Set<Group> groups,
			Pageable pageable) throws SklayException {

		Query countQuery = initSearchMedicalReport(keyword, tipType, readType,
				user, groups, true);
		Query dataQuery = initSearchMedicalReport(keyword, tipType, readType,
				user, groups, false);

		Long total = (Long) countQuery.getSingleResult();
		if (!(total > 0))
			return null;
		dataQuery.setFirstResult(pageable.getOffset());
		dataQuery.setMaxResults(pageable.getPageSize());
		List<MedicalReport> resultList = dataQuery.getResultList();

		return new PageImpl(resultList, pageable, total);
	}

	private Query initSearchMedicalReport(String keyword, TipType tipType,
			ReadType readType, User user, Set<Group> groups, boolean count) {
		StringBuffer sb = null;
		if (count)
			sb = new StringBuffer(
					"select count( m ) from MedicalReport m where 1=1 ");
		else
			sb = new StringBuffer("select m from MedicalReport m where 1=1 ");

		if (null != tipType)
			sb.append(" and m.tipType = :tipType ");

		if (null != readType)
			sb.append(" and m.read = :readType ");

		if (null != user)
			sb.append(" and m.targetUser.group.owner = :owner ");

		if (CollectionUtils.isNotEmpty(groups))
			sb.append(" and m.targetUser.group in ( :groups ) ");

		if (StringUtils.isNotBlank(keyword)) {
			sb.append(" and ( m.targetUser.name like :keyword or  m.targetUser.phone like :keyword )");
		}

		sb.append(" order by m.reportTime desc ");

		Query query = em.createQuery(sb.toString());

		if (null != tipType)
			query.setParameter("tipType", tipType);

		if (null != readType)
			query.setParameter("readType", readType);

		if (null != user)
			query.setParameter("owner", user);

		if (CollectionUtils.isNotEmpty(groups))
			query.setParameter("groups", groups);

		if (StringUtils.isNotBlank(keyword)) {
			query.setParameter("keyword", "%"
					+ keyword.replaceAll("%", "").trim() + "%");
		}
		return query;
	}

	private Query initMataDataSearch(Integer minAge, Integer maxAge, Sex sex,
			Integer lowPressure, Integer highPressure, String keyword,
			boolean count) {
		StringBuffer sb = null;
		if (count)
			sb = new StringBuffer(
					"select count( m ) from MataData m where 1=1 ");
		else
			sb = new StringBuffer("select m from MataData m where 1=1 ");
		if ((minAge != null && maxAge != null) && (minAge > 0 && maxAge > 0))
			sb.append(" and m.ageMin = :ageMin and  m.ageMax  = :ageMax  ");
		else if ((minAge != null && minAge > 0)
				|| (maxAge != null && maxAge > 0))
			sb.append(" and m.ageMin<= :age and  m.ageMax >= :age  ");
		if (null != sex)
			sb.append(" and m.sex = :sex ");

		if ((lowPressure != null && highPressure != null)
				&& (highPressure > 0 && lowPressure > 0))
			sb.append(" and m.lowPressureMin <= :lowPressure and m.lowPressureMax >= :lowPressure and m.highPressureMin <= :highPressure and m.highPressureMax >= :lowPressure  ");

		if (StringUtils.isNotBlank(keyword)) {
			sb.append(" and m.result like :keyword or m.remark like :keyword ");
		}

		sb.append(" order by m.ageMin asc ");

		Query query = em.createQuery(sb.toString());

		if ((minAge != null && maxAge != null) && (minAge > 0 && maxAge > 0)) {
			query.setParameter("ageMin", minAge);
			query.setParameter("ageMax", maxAge);
		} else if ((minAge != null && minAge > 0)
				|| (maxAge != null && maxAge > 0)) {
			int age = (minAge != null && minAge > 0) ? minAge : maxAge;
			query.setParameter("age", age);
		}

		if (null != sex)
			query.setParameter("sex", sex);

		if ((lowPressure != null && highPressure != null)
				&& (highPressure > 0 && lowPressure > 0)) {
			query.setParameter("lowPressure", lowPressure);
			query.setParameter("highPressure", highPressure);
		}
		if (StringUtils.isNotBlank(keyword)) {
			query.setParameter("keyword", "%"
					+ keyword.replaceAll("%", "").trim() + "%");
		}
		return query;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page<DeviceBinding> getDeviceBindingPage(Long groups,
			String keyword, Level level, BindingMold bindingMold, User creator,
			AuditStatus status, AuditStatus moldStatus, Long belong,
			Pageable pageable) throws SklayException {

		Query countQuery = initDevicePage(groups, keyword, level, bindingMold,
				creator, status, moldStatus, belong, true);
		Query dataQuery = initDevicePage(groups, keyword, level, bindingMold,
				creator, status, moldStatus, belong, false);

		Long total = (Long) countQuery.getSingleResult();
		if (!(total > 0))
			return null;
		dataQuery.setFirstResult(pageable.getOffset());
		dataQuery.setMaxResults(pageable.getPageSize());

		List<DeviceBinding> resultList = dataQuery.getResultList();

		return new PageImpl(resultList, pageable, total);

	}

	private Query initDevicePage(Long group, String keyword, Level level,
			BindingMold bindingMold, User creator, AuditStatus status,
			AuditStatus moldStatus, Long belong, boolean count) {

		StringBuffer sb = null;
		if (count)
			sb = new StringBuffer(
					"select count(d) from DeviceBinding d where 1=1 ");
		else
			sb = new StringBuffer("select d from DeviceBinding d where 1=1 ");

		if (null != level)
			sb.append(" and d.level = :level ");

		if (null != bindingMold)
			sb.append(" and d.mold = :bindingMold ");

		if (null != moldStatus) {
			sb.append(" and d.moldStatus = :moldStatus ");
			sb.append(" and d.mold = :bindingMold ");
		}
		if (null != status)
			sb.append(" and d.status = :status ");

		if (null != belong) {
			if (null != creator)
				sb.append(" and ( d.creator = :creator or  d.belong = :belong )");
			else
				sb.append(" and d.belong = :belong  ");
		} else if (null != creator)
			sb.append(" and d.creator = :creator )");

		if (null != group)
			sb.append(" and d.targetUser.group.id = :group ");

		if (StringUtils.isNotBlank(keyword))
			sb.append(" and ( d.targetUser.name like :keyword or d.serialNumber like :keyword ) ");

		Query query = em.createQuery(sb.toString());

		if (null != level)
			query.setParameter("level", level);

		if (null != bindingMold)
			query.setParameter("bindingMold", bindingMold);

		if (null != moldStatus) {
			query.setParameter("moldStatus", moldStatus);
			query.setParameter("bindingMold", BindingMold.PAID);
		}
		if (null != status)
			query.setParameter("status", status);

		if (null != belong) {
			query.setParameter("belong", belong);
			if (null != creator)
				query.setParameter("creator", creator);
		} else if (null != creator)
			query.setParameter("creator", creator);

		if (null != group)
			query.setParameter("group", group);

		if (StringUtils.isNotBlank(keyword))
			query.setParameter("keyword", "%"
					+ keyword.replaceAll("%", "").trim() + "%");

		return query;
	}

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
	public Map<Long, Long> findMemberCount(Set<User> owner) {

		Map<Long, Long> result = Maps.newHashMap();

		StringBuffer sb = new StringBuffer(
				"select g from Group g where g.owner in (:owner)  ");
		Query query = em.createQuery(sb.toString());

		query.setParameter("owner", owner);

		List<Group> list = query.getResultList();

		if (CollectionUtils.isEmpty(list))
			return result;

		for (Group g : list) {
			User o = g.getOwner();
			Long oId = o.getId();
			if (owner.contains(o)) {
				Long count = result.containsKey(oId) ? result.get(oId)
						+ g.getMemberCount() : g.getMemberCount();
				result.put(oId, count);
				continue;
			}

			result.put(oId, 0L);
		}

		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page<User> findMemberPage(Long group, String keyword, User creator,
			Long belong, Pageable pageable) {

		Query countQuery = initFindMemberPage(group, keyword, creator, belong,
				true);
		Query dataQuery = initFindMemberPage(group, keyword, creator, belong,
				false);

		Long total = (Long) countQuery.getSingleResult();
		if (!(total > 0))
			return null;
		dataQuery.setFirstResult(pageable.getOffset());
		dataQuery.setMaxResults(pageable.getPageSize());

		List<User> resultList = dataQuery.getResultList();

		return new PageImpl(resultList, pageable, total);
	}

	private Query initFindMemberPage(Long group, String keyword, User creator,
			Long belong, boolean isCount) {
		StringBuffer qlString = new StringBuffer(" select ");
		if (isCount)
			qlString.append(" count( DISTINCT u) ");
		else
			qlString.append(" DISTINCT u ");

		qlString.append(" from User u where 1=1 ");

		if (null != group)
			qlString.append(" and u.group.id = :group ");

		if (null != belong) {
			if (null != creator) {
				qlString.append(" and u.group.owner = :owner  ");
			} else
				qlString.append(" and u.belong = :belong  ");
		} else if (null != creator)
			qlString.append(" and u.creator = :creator )");

		if (StringUtils.isNotBlank(keyword))
			qlString.append(" and ( u.phone like :keyword or u.name like :keyword or u.area like :keyword  or u.address like :keyword  or u.description like :keyword  ) ");

		Query query = em.createQuery(qlString.toString());

		if (null != group)
			query.setParameter("group", group);

		if (null != belong) {
			if (null != creator)
				query.setParameter("owner", creator);
			else
				query.setParameter("belong", belong);
		} else if (null != creator)
			query.setParameter("owner", creator);

		if (StringUtils.isNotBlank(keyword))
			query.setParameter("keyword",
					"%" + keyword.trim().replaceAll("%", "") + "%");

		return query;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page<Operation> getOperationPage(LogLevelType levelType,
			Pageable pageable) throws SklayException {

		Query countQuery = initFindLogPage(levelType, true);
		Query dataQuery = initFindLogPage(levelType, false);

		Long total = (Long) countQuery.getSingleResult();
		if (!(total > 0))
			return null;
		dataQuery.setFirstResult(pageable.getOffset());
		dataQuery.setMaxResults(pageable.getPageSize());

		List<User> resultList = dataQuery.getResultList();

		return new PageImpl(resultList, pageable, total);
	}

	private Query initFindLogPage(LogLevelType levelType, boolean isCount) {

		StringBuffer qlString = new StringBuffer(" select ");
		if (isCount)
			qlString.append(" count(o) ");
		else
			qlString.append(" o ");

		qlString.append(" from Operation o where 1=1 ");

		if (null != levelType)
			qlString.append(" and o.type = :type  ");

		qlString.append(" order by  o.createTime desc ");

		Query query = em.createQuery(qlString.toString());

		if (null != levelType)
			query.setParameter("type", levelType);

		return query;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page<Application> findAppPage(String keyword, AppType appType,
			AuditStatus status, Long owner, Pageable pageable)
			throws SklayException {

		Query queryCount = initAppPage(keyword, appType, status, owner, true);
		Query queryData = initAppPage(keyword, appType, status, owner, false);

		Long total = (Long) queryCount.getSingleResult();
		if (!(total > 0))
			return null;
		queryData.setFirstResult(pageable.getOffset());
		queryData.setMaxResults(pageable.getPageSize());

		List<Application> resultList = queryData.getResultList();

		return new PageImpl(resultList, pageable, total);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Application> getByCreator(AppType appType, AuditStatus status,
			Long owner) throws SklayException {

		Query queryData = initAppPage(null, appType, status, owner, false);

		List<Application> resultList = queryData.getResultList();

		return resultList;
	}

	private Query initAppPage(String keyword, AppType appType,
			AuditStatus status, Long creator, boolean isCount) {

		StringBuffer qlString = new StringBuffer(" select ");
		if (isCount)
			qlString.append(" count(a) ");
		else
			qlString.append(" a ");

		qlString.append(" from Application a where 1=1 ");

		if (null != appType)
			qlString.append(" and a.appType = :appType  ");

		if (null != status)
			qlString.append(" and a.status = :status  ");

		if (null != creator)
			qlString.append(" and a.owner = :creator  ");

		qlString.append(" order by  a.updatorTime desc ");

		Query query = em.createQuery(qlString.toString());

		if (null != appType)
			query.setParameter("appType", appType);

		if (null != status)
			query.setParameter("status", status);

		if (null != creator)
			query.setParameter("creator", creator);

		return query;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page<SMS> getSMSPage(Application app, SMSStatus status,
			User crteator, Long belong, Pageable pageable)
			throws SklayException {

		Query queryCount = initSMSPage(app, status, crteator, belong, true);
		Query queryData = initSMSPage(app, status, crteator, belong, false);

		Long total = (Long) queryCount.getSingleResult();
		if (!(total > 0))
			return null;
		queryData.setFirstResult(pageable.getOffset());
		queryData.setMaxResults(pageable.getPageSize());

		List<SMS> resultList = queryData.getResultList();

		return new PageImpl(resultList, pageable, total);
	}

	private Query initSMSPage(Application app, SMSStatus status, User creator,
			Long belong, boolean isCount) {

		StringBuffer qlString = new StringBuffer(" select ");
		if (isCount)
			qlString.append(" count(m) ");
		else
			qlString.append(" m ");

		qlString.append(" from SMS m where 1=1 ");

		if (null != status)
			qlString.append(" and m.status = :status  ");

		if (null != app)
			qlString.append(" and m.app = :app  ");

		if (null != belong) {
			if (null != creator)
				qlString.append(" and ( m.creator.id = :creator or  m.belong = :belong )");
			else
				qlString.append(" and m.belong = :belong  ");
		} else if (null != creator)
			qlString.append(" and m.creator.id = :creator )");

		qlString.append(" order by  m.sendTime desc ");

		Query query = em.createQuery(qlString.toString());

		if (null != status)
			query.setParameter("status", status);

		if (null != app)
			query.setParameter("app", app);

		if (null != belong) {
			if (null != creator) {
				query.setParameter("creator", creator.getId());
				query.setParameter("belong", belong);
			} else
				query.setParameter("belong", belong);
		} else if (null != creator)
			query.setParameter("creator", creator);

		return query;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeviceBinding> findTargetBinding(String phone, Level level) {

		StringBuffer qlString = new StringBuffer(
				" select b from DeviceBinding b where 1=1 ");

		if (StringUtils.isNotBlank(phone))
			qlString.append(" and b.targetUser.phone = :phone  ");

		if (null != level)
			qlString.append(" and b.level = :level  ");

		qlString.append(" order by  b.updateTime desc ");

		Query query = em.createQuery(qlString.toString());

		if (StringUtils.isNotBlank(phone))
			query.setParameter("phone", phone.trim());

		if (null != level)
			query.setParameter("level", level);

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeviceBinding> getUserBindingByLevel(Set<Long> targetUser,
			Level level, String sn) throws SklayException {

		StringBuffer sb = new StringBuffer(
				" select b from DeviceBinding b where 1=1 ");

		if (CollectionUtils.isNotEmpty(targetUser))
			sb.append(" and b.targetUser.id in ( :targetUser ) ");
		if (null != level)
			sb.append(" and b.level = :level ");
		if (StringUtils.isNotBlank(sn))
			sb.append(" and b.serialNumber = :sn ");

		Query query = em.createQuery(sb.toString());
		if (CollectionUtils.isNotEmpty(targetUser))
			query.setParameter("targetUser", targetUser);
		if (null != level)
			query.setParameter("level", level);
		if (StringUtils.isNotBlank(sn))
			query.setParameter("sn", sn.trim());
		return query.getResultList();

	}

}
