package com.sklay.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sklay.core.enums.AuditStatus;
import com.sklay.core.ex.SklayException;
import com.sklay.model.Festival;
import com.sklay.model.SMSTemplate;

public interface SMSTemplateDao extends JpaRepository<SMSTemplate, Long> {

	@Query(" select s from SMSTemplate s where s.id in ( ?1 ) ")
	public List<SMSTemplate> list(Set<Long> ids);

	@Query(" select s from SMSTemplate s where s.status = ?1 and s.festival = ?2 ")
	public List<SMSTemplate> list(AuditStatus status, Festival festival);

	@Query(" select s from SMSTemplate s where s.tpl = ?1  and s.festival = ?2 ")
	public List<SMSTemplate> list(String tpl, Festival festival)
			throws SklayException;

	@Modifying
	@Query(" update SMSTemplate s set s.status = ?1 where s.id in ( ?2 ) ")
	public void offOn(AuditStatus status, Set<Long> festival)
			throws SklayException;
}
