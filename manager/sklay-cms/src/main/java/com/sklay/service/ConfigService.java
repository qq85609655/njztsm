package com.sklay.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sklay.model.Config;
import com.sklay.model.ConfigPK;

public interface ConfigService {

	Config findOne(ConfigPK cpk);

	<T> T findData(ConfigPK cpk, Class<T> clazz);

	void save(Config config);

	void save(ConfigPK cpk, Object data);

	Iterable<Config> findAll(List<ConfigPK> cpks);

	Page<Config> findByBiz(Pageable pageable, final String biz,
			Set<Integer> status);

}
