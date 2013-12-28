package com.sklay.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sklay.core.enums.SMSStatus;
import com.sklay.dao.SMSDao;
import com.sklay.dao.SpecificDao;
import com.sklay.model.Application;
import com.sklay.model.SMS;
import com.sklay.model.User;
import com.sklay.service.SMSService;

@Service
public class SMSServiceImpl implements SMSService {

	@Autowired
	private SMSDao smsDao;

	@Autowired
	private SpecificDao specificDao;

	@Override
	public List<SMS> create(Set<SMS> list) {
		return smsDao.save(list);
	}

	@Override
	public Page<SMS> getSMSPage(Application app, SMSStatus status,
			User creator, Long belong, Pageable pageable) {
		return specificDao.getSMSPage(app, status, creator, belong, pageable);
	}

	@Override
	public void removeSMS(User creator) {
		smsDao.removeSMS(creator);
	}

	@Override
	public SMS getSMS(Long id) {
		return smsDao.findOne(id);
	}

	@Override
	public SMS update(SMS sms) {
		return smsDao.save(sms);
	}

	@Override
	public void update(Set<SMS> sms) {
		smsDao.save(sms);
	}
}
