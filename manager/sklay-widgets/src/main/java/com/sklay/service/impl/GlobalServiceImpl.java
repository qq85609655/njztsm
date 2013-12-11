package com.sklay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sklay.dao.GlobalSettingDao;
import com.sklay.dao.SpecificDao;
import com.sklay.model.GlobalSetting;
import com.sklay.service.GlobalService;

@Service
public class GlobalServiceImpl implements GlobalService {

	@Autowired
	private GlobalSettingDao globalSettingDao;

	@Autowired
	private SpecificDao specificDao;

	@Override
	public GlobalSetting findOne(Long id) {
		return globalSettingDao.findOne(id);
	}

	@Override
	public void update(GlobalSetting globalSetting) {
		globalSettingDao.saveAndFlush(globalSetting);
	}

	@Override
	public GlobalSetting getGlobalConfig() {
		return specificDao.getGlobalConfig();
	}

}
