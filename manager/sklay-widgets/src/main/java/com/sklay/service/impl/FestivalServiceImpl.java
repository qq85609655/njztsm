package com.sklay.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sklay.core.enums.SwitchStatus;
import com.sklay.core.ex.SklayException;
import com.sklay.dao.FestivalDao;
import com.sklay.dao.SpecificDao;
import com.sklay.model.Festival;
import com.sklay.service.FestivalService;

@Service
public class FestivalServiceImpl implements FestivalService {

	@Resource
	private FestivalDao festivalDao;

	@Resource
	private SpecificDao specificDao;

	@Override
	public Festival create(Festival festival) {
		if (null != festival) {
			festival.setId(null);
			return festivalDao.save(festival);
		}
		return null;
	}

	@Override
	public Festival update(Festival festival) {
		if (null != festival && null != festival.getId()) {
			return festivalDao.save(festival);
		}
		return null;
	}

	@Override
	public Page<Festival> getFestivalPage(String jobTime,
			SwitchStatus switchStatus, String keyword, Pageable pageable)
			throws SklayException {
		return specificDao.getFestivalPage(jobTime, switchStatus, keyword,
				pageable);
	}

	@Override
	public Festival get(Long festival) throws SklayException {
		return festivalDao.findOne(festival);
	}

	@Override
	public List<Festival> list(Set<Long> festival) throws SklayException {
		return festivalDao.list(festival);
	}

	@Override
	public void offOn(Set<Long> festival, SwitchStatus switchStatus)
			throws SklayException {
		festivalDao.offOn(switchStatus, festival);
	}

	@Override
	public List<Festival> list(String jobTime) throws SklayException {
		return festivalDao.list(jobTime);
	}

	@Override
	public List<Festival> list(String jobTime, SwitchStatus switchStatus)
			throws SklayException {
		return festivalDao.list(jobTime, switchStatus);
	}
}
