package com.proper.uip.security.operation.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.security.entity.Resource;
import com.proper.uip.security.dao.ResourcesDao;
import com.proper.uip.security.operation.service.OperationService;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class OperationServiceImpl implements OperationService {
	
	@Autowired
	private ResourcesDao operationDao;

	@Override
	public Resource getOperationById(String id) {
		return operationDao.get(id);
	}

}
