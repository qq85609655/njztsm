package com.sklay.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sklay.dao.BlackwordDao;
import com.sklay.model.Blackword;
import com.sklay.service.BlackwordService;

@Service
public class BlackwordServiceImpl implements BlackwordService {

	@Autowired
	private BlackwordDao blackwordDao;

	@Override
	public List<Blackword> blackwordList() {
		return blackwordDao.findAll();
	}
}
