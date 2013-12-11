package com.sklay.service;

import com.sklay.model.GlobalSetting;

public interface GlobalService {

	public GlobalSetting findOne(Long id);

	public GlobalSetting getGlobalConfig();

	public void update(GlobalSetting globalSetting);
}
