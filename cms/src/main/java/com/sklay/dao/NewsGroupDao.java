package com.sklay.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.sklay.model.NewsGroup;

public interface NewsGroupDao extends JpaRepository<NewsGroup, Long> {

	@QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
	List<NewsGroup> findByPid(Long pid);

	@QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
	List<NewsGroup> findByBizAndOwnerAndPidIsNullAndLeafFalse(String biz,
			String owner);

	NewsGroup findOneByBizAndOwnerAndLeafTrue(String biz, String owner);

	@QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
	List<NewsGroup> findByLeafTrueAndPathStartingWith(String path);

	@QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
	List<NewsGroup> findByBizAndOwnerAndPidAndLeafFalse(String biz,
			String owner, Long pid);

	@QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
	List<NewsGroup> findByBizAndOwnerAndLeafTrue(String biz, String owner);

	@Query("delete from NewsGroup where path like ?1")
	@Modifying
	void deleteByPathLike(String path);

	@QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
	List<NewsGroup> findByIdIn(List<Long> groupIds);

}
