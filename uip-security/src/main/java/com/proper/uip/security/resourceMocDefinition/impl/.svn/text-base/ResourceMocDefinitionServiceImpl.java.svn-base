package com.proper.uip.security.resourceMocDefinition.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proper.uip.api.security.entity.ResourceMocDefinition;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
import com.proper.uip.security.dao.ResourceMocDefinitionDao;
import com.proper.uip.security.resourceMocDefinition.service.ResourceMocDefinitionService;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class ResourceMocDefinitionServiceImpl implements ResourceMocDefinitionService {

	@Autowired
	private ResourceMocDefinitionDao resourceMocDefinitionDao;

	@Override
	public Page<ResourceMocDefinition> findAllResourceMocDefinition(
			PageConfig createPageConfig) {
		String sql = "select c from ResourceMocDefinition c";
		Page<ResourceMocDefinition> resourceMocDefinitionList = resourceMocDefinitionDao.findPage(createPageConfig, sql);
		return resourceMocDefinitionList;
	}

	@Override
	public void saveResourceMocDefinition(
			ResourceMocDefinition resourceMocDefinitionEntity) {
		resourceMocDefinitionDao.save(resourceMocDefinitionEntity);
	}

	@Override
	public ResourceMocDefinition getResourceMocDefinitionById(String id) {
		return resourceMocDefinitionDao.get(id);
	}

	@Override
	public void deleteResourceMocDefinitionById(String id) {
		resourceMocDefinitionDao.delete(id);
	}

	@Override
	public ResourceMocDefinition getByName(String name) {
		String sql = "select c from ResourceMocDefinition c where c.name = ?";
		ResourceMocDefinition uName = resourceMocDefinitionDao.findUnique(sql, name);
		return uName;
	}
}
