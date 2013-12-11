package com.sklay.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sklay.core.enums.AuditStatus;
import com.sklay.core.enums.MemberRole;
import com.sklay.model.Group;
import com.sklay.model.User;

public interface GroupDao extends JpaRepository<Group, Long> {

	@Query("select g from Group g where g.name like ?1 or g.description like ?1 ")
	Page<Group> findKeywordAll(String keyword, Pageable pageable);

	@Query("select g from Group g where g.owner = ?1 and g.name like ?2 or g.description like ?2 ")
	Page<Group> findKeywordAll(User owner, String keyword, Pageable pageable);

	@Query("select g from Group g where g.owner = ?1 ")
	Page<Group> findGroupByOwner(User owner, Pageable pageable);

	@Query("select g from Group g where g.owner = ?1 ")
	List<Group> findGroupByOwner(User owner);

	@Query("select g from Group g where g.parentGroupId in ( ?1 ) ")
	List<Group> findGroupByParentId(Set<Long> parentId);

	@Query("select g from Group g where g.status = ?1 and g.role = ?2 and g.parentGroupId is null ")
	Group findSuperAdminGroup(AuditStatus auditStatus, MemberRole role);

	@Modifying
	@Query(" delete from Group g where g.owner = ?1 ")
	void deleteByUser(User user);
}
