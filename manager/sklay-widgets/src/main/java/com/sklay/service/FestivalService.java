package com.sklay.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sklay.core.enums.SwitchStatus;
import com.sklay.core.ex.SklayException;
import com.sklay.model.Festival;

public interface FestivalService {

	public Festival create(Festival festival) throws SklayException;

	public Festival update(Festival festival) throws SklayException;

	public Festival get(Long festival) throws SklayException;

	public List<Festival> list(Set<Long> festival) throws SklayException;

	public List<Festival> list(String jobTime) throws SklayException;

	public void offOn(Set<Long> festival, SwitchStatus switchStatus)
			throws SklayException;

	public Page<Festival> getFestivalPage(String jobTime,
			SwitchStatus switchStatus, String keyword, Pageable pageable)
			throws SklayException;
}
