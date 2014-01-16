package com.sklay.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sklay.core.enums.AuditStatus;
import com.sklay.core.ex.SklayException;
import com.sklay.model.Festival;
import com.sklay.model.SMSTemplate;

public interface SMSTemplateService {

	public SMSTemplate create(SMSTemplate template) throws SklayException;

	public SMSTemplate update(SMSTemplate template) throws SklayException;

	public SMSTemplate get(Long template) throws SklayException;

	public List<SMSTemplate> list(Set<Long> template) throws SklayException;

	public List<SMSTemplate> list(AuditStatus status, Festival festival);

	public List<SMSTemplate> list(String tpl, Festival festival)
			throws SklayException;

	public Page<SMSTemplate> getSMSTemplatePage(String jobTime,
			AuditStatus status, Festival festival, String keyword,
			Pageable pageable) throws SklayException;
}
