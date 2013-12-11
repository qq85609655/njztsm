package com.sklay.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sklay.model.Application;
import com.sklay.model.User;

public interface ApplicationDao extends JpaRepository<Application, Long> {

	@Modifying
	@Query(" delete from Application a where a.creator = ?1 or a.updator= ?1 ")
	public void remove(User creator);
}
