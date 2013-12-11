package com.sklay.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sklay.model.UserAttr;

public interface UserAttrDao extends JpaRepository<UserAttr, Long> {

	@Modifying
	@Query("delete from UserAttr u where u.userId = ?1 ")
	public void removeAttrByUser(Long userId);

	@Query("select u from UserAttr u where u.key = ?1 and u.userId = ?2 ")
	public List<UserAttr> findUserAttrByKey(String key, Long userId);
	
	@Query("select u from UserAttr u where u.key = ?1 and u.userId in (?2) ")
	public List<UserAttr> findUserPaidAttrByKey(String key, Set<Long> userIds);
}
