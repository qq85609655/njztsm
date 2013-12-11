package com.sklay.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sklay.model.Config;
import com.sklay.model.ConfigPK;

public interface ConfigDao extends JpaRepository<Config, ConfigPK>,
		JpaSpecificationExecutor<Config> {

}
