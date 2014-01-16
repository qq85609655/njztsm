package com.sklay.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sklay.core.enums.AuditStatus;
import com.sklay.core.ex.SklayException;
import com.sklay.dao.SMSTemplateDao;
import com.sklay.dao.SpecificDao;
import com.sklay.model.Festival;
import com.sklay.model.SMSTemplate;
import com.sklay.service.SMSTemplateService;

@Service
public class SMSTemplateServiceImpl implements SMSTemplateService {

	@Resource
	private SMSTemplateDao smsTemplateDao;

	@Resource
	private SpecificDao specificDao;

	@Override
	public SMSTemplate create(SMSTemplate festival) {
		if (null != festival) {
			festival.setId(null);
			return smsTemplateDao.save(festival);
		}
		return null;
	}

	@Override
	public SMSTemplate update(SMSTemplate festival) {
		if (null != festival && null != festival.getId()) {
			return smsTemplateDao.save(festival);
		}
		return null;
	}

	@Override
	public Page<SMSTemplate> getSMSTemplatePage(String jobTime,
			AuditStatus status, Festival festival, String keyword,
			Pageable pageable) throws SklayException {
		return specificDao.getSMSTemplatePage(status, festival, keyword,
				pageable);
	}

	@Override
	public SMSTemplate get(Long festival) throws SklayException {
		return smsTemplateDao.findOne(festival);
	}

	@Override
	public List<SMSTemplate> list(Set<Long> festival) throws SklayException {
		return smsTemplateDao.list(festival);
	}

	@Override
	public List<SMSTemplate> list(AuditStatus status, Festival festival) {
		return smsTemplateDao.list(status, festival);
	}

	@Override
	public List<SMSTemplate> list(String tpl, Festival festival)
			throws SklayException {
		return smsTemplateDao.list(tpl, festival);
	}
}
