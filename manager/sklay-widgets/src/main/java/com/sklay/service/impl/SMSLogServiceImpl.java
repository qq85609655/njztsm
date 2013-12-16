package com.sklay.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sklay.core.enums.SMSStatus;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.dao.SMSLogDao;
import com.sklay.dao.SpecificDao;
import com.sklay.model.SMSLog;
import com.sklay.model.User;
import com.sklay.service.SMSLogService;

@Service
public class SMSLogServiceImpl implements SMSLogService {

	Logger logger = LoggerFactory.getLogger(SMSLogServiceImpl.class);

	@Autowired
	private SMSLogDao smsLogDao;

	@Autowired
	private SpecificDao specificDao;

	@Override
	public void createSMSLog(Set<SMSLog> logSet) {
		if (CollectionUtils.isEmpty(logSet))
			throw new SklayException(ErrorCode.ILLEGAL_PARAM);
		smsLogDao.save(logSet);
		logger.info("create smslog");
	}

	@Override
	public Page<SMSLog> getSMSPage(String keyword, Date startDate,
			Date endDate, SMSStatus status, Long userOwner, Set<Long> gropuId,
			Pageable pageable) {
		return specificDao.getSMSPage(keyword, startDate, endDate, status,
				userOwner,gropuId, pageable);
	}

	@Override
	public List<SMSLog> getSMSList(Set<Long> ids) {
		return (List<SMSLog>) smsLogDao.findAll(ids);
	}

	@Override
	public SMSLog getSMS(Long id) {
		return smsLogDao.findOne(id);
	}

	@Override
	public void updateSMSLog(SMSLog log) {
		smsLogDao.saveAndFlush(log);
	}

	@Override
	public void deleteSMSLog(Long userId) {
		smsLogDao.deleteSMSLog(userId);
	}

	@Override
	public void deleteByReceiver(User receiver) {
		smsLogDao.deleteByReceiver(receiver);
	}

}
