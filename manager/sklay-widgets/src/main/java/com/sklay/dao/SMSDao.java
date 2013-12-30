package com.sklay.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sklay.model.SMS;

public interface SMSDao extends JpaRepository<SMS, Long> {

	@Modifying
	@Query(" delete from SMS s where s.creator = ?1")
	public void removeSMS(Long creator);
}
