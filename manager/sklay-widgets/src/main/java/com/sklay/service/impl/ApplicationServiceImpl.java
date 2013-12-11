package com.sklay.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sklay.core.enums.AppType;
import com.sklay.core.enums.AuditStatus;
import com.sklay.core.ex.SklayException;
import com.sklay.dao.ApplicationDao;
import com.sklay.dao.SpecificDao;
import com.sklay.model.Application;
import com.sklay.model.User;
import com.sklay.service.ApplicationService;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationDao appDao;

	@Autowired
	private SpecificDao specificDao;

	@Override
	public Application cerate(Application application) throws SklayException {
		if (null == application)
			return null;
		application.setId(null);
		return appDao.saveAndFlush(application);
	}

	@Override
	public Application update(Application application) throws SklayException {
		if (null == application)
			return null;
		return appDao.saveAndFlush(application);
	}

	@Override
	public Page<Application> getPage(String keyword, AppType appType,
			AuditStatus status, User creator, Pageable pageable)
			throws SklayException {
		return specificDao.findAppPage(keyword, appType, status, creator,
				pageable);
	}

	@Override
	public Application get(Long id) throws SklayException {
		if (null == id)
			return null;
		return appDao.findOne(id);
	}

	@Override
	public List<Application> getByCreator(AppType appType, AuditStatus status,
			User creator) throws SklayException {
		return specificDao.getByCreator(appType, status, creator);
	}

	@Override
	public void remove(User creator) throws SklayException {
		appDao.remove(creator);
	}
}
