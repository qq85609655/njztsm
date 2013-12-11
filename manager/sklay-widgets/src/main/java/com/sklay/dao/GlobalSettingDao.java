package com.sklay.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sklay.model.GlobalSetting;

public interface GlobalSettingDao extends JpaRepository<GlobalSetting, Long> {

}
