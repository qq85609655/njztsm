package com.sklay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sklay.dao.OperationDao;
import com.sklay.model.Operation;
import com.sklay.service.OperationService;

@Service
public class OperationServiceImpl implements OperationService {

	@Autowired
	private OperationDao operationDao;

	@Override
	public void create(Operation operation) {
		if (null != operation) {
			operation.setId(null);
			operationDao.save(operation);
		}
	}
}
