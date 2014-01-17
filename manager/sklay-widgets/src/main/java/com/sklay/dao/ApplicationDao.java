package com.sklay.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sklay.core.enums.AppType;
import com.sklay.model.Application;

public interface ApplicationDao extends JpaRepository<Application, Long> {

	@Modifying
	@Query(" delete from Application a where a.owner = ?1 or a.updator= ?1 ")
	public void remove(Long owner);

	@Query(" select a from Application a where a.appType = ?1 and a.owner= ?2 ")
	public Application getByCreator(AppType appType, Long owner);

	@Query(" select a from Application a where a.appType = ?1 ")
	public List<Application> getByAppType(AppType appType);
}
