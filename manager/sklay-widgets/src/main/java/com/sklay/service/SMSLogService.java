package com.sklay.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sklay.core.enums.SMSStatus;
import com.sklay.model.SMSLog;
import com.sklay.model.User;

public interface SMSLogService {

	public void updateSMSLog(SMSLog log);

	public void createSMSLog(Set<SMSLog> logSet);

	public List<SMSLog> getSMSList(Set<Long> ids);

	public SMSLog getSMS(Long id);

	public void deleteSMSLog(Long userId);

	public void deleteByReceiver(User receiver);

	public Page<SMSLog> getSMSPage(String keyword, Date startDate,
			Date endDate, SMSStatus status, Long userOwner, Pageable pageable);
}
