package com.sklay.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sklay.core.enums.Sex;
import com.sklay.core.ex.SklayException;
import com.sklay.model.MataData;

public interface MatadataService {

	MataData create(MataData mataData) throws SklayException;

	void update(MataData mataData) throws SklayException;

	boolean exist(MataData mataData) throws SklayException;

	MataData getMataData(Long id) throws SklayException;

	List<MataData> getMataData(Set<Long> id) throws SklayException;

	void delete(Set<MataData> id) throws SklayException;

	List<MataData> queryUserMataData(int age, Sex sex, int lowPressure,
			int highPressure) throws SklayException;

	Page<MataData> getMataDataPage(Integer minAge, Integer maxAge, Sex sex,
			Integer lowPressure, Integer highPressure, String keyword,
			Pageable pageable) throws SklayException;
}
