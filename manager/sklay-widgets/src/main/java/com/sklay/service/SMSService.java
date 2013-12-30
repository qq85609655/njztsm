package com.sklay.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sklay.core.enums.SMSStatus;
import com.sklay.model.Application;
import com.sklay.model.SMS;
import com.sklay.model.User;

public interface SMSService {

	public List<SMS> create(Set<SMS> list);

	public SMS update(SMS sms);

	public void update(Set<SMS> sms);

	public Page<SMS> getSMSPage(Application app, SMSStatus status,
			User creator, Long belong, Pageable pageable);

	public void removeSMS(Long creator);

	public SMS getSMS(Long id);
}
