package com.sklay.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sklay.model.SMSLog;
import com.sklay.model.User;

public interface SMSLogDao extends JpaRepository<SMSLog, Long> {

	@Modifying
	@Query("delete from SMSLog s where s.user.id = ?1 ")
	public void deleteSMSLog(Long userId);

	@Modifying
	@Query("delete from SMSLog s where s.receiver = ?1 ")
	public void deleteByReceiver(User receiver);
}
