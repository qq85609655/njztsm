package com.sklay.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sklay.core.enums.Sex;
import com.sklay.core.ex.SklayException;
import com.sklay.dao.MatadataDao;
import com.sklay.dao.SpecificDao;
import com.sklay.model.MataData;
import com.sklay.service.MatadataService;

@Service
public class MataDataServiceImpl implements MatadataService {

	@Autowired
	private MatadataDao matadataDao;

	@Autowired
	private SpecificDao specificDao;

	private final static int ZERO = 0;

	@SuppressWarnings("unchecked")
	@Override
	public List<MataData> queryUserMataData(int age, Sex sex, int lowPressure,
			int highPressure) throws SklayException {
		if (ZERO >= age || null == sex || ZERO >= lowPressure
				|| ZERO >= highPressure)
			return Collections.EMPTY_LIST;

		return matadataDao.queryUserMataData(age, sex, lowPressure,
				highPressure);
	}

	@Override
	public Page<MataData> getMataDataPage(Integer minAge, Integer maxAge,
			Sex sex, Integer lowPressure, Integer highPressure, String keyword,
			Pageable pageable) throws SklayException {
		return specificDao.getMataDataPage(minAge, maxAge, sex, lowPressure,
				highPressure, keyword, pageable);
	}

	@Override
	public MataData getMataData(Long id) throws SklayException {
		return matadataDao.findOne(id);
	}

	@Override
	public List<MataData> getMataData(Set<Long> ids) throws SklayException {
		return (List<MataData>) matadataDao.findAll(ids);
	}

	@Override
	public void delete(Set<MataData> md) throws SklayException {
		matadataDao.deleteInBatch(md);
	}

	@Override
	public MataData create(MataData mataData) throws SklayException {
		mataData.setId(null);
		return matadataDao.saveAndFlush(mataData);
	}

	@Override
	public void update(MataData mataData) throws SklayException {
		matadataDao.saveAndFlush(mataData);
	}

	@Override
	public boolean exist(MataData mataData) throws SklayException {
		return matadataDao.countMataData(mataData.getAgeMin(),
				mataData.getAgeMax(), mataData.getSex(),
				mataData.getLowPressureMin(), mataData.getLowPressureMax(),
				mataData.getHighPressureMin(), mataData.getHighPressureMax()) > 0;
	}

}
