package com.proper.uip.security.resourceSet.service;

import java.util.List;

import com.proper.uip.api.security.entity.ResourceSet;
import com.proper.uip.api.security.entity.ResourceTreeNode;
import com.proper.uip.common.utils.Page;
import com.proper.uip.common.utils.PageConfig;
public interface ResourceSetService {


	Page<ResourceSet> findAllResourceSet(PageConfig pageConfig, String name);

	void saveResourceSet(ResourceSet resourceSetEntity);

	List<ResourceTreeNode> buildResourceTree(String resourceSetId, boolean chkDisabled);

	void deleteResourceSetById(String id);

	List<ResourceSet> getAllResourceSet();
	
	ResourceSet getById(String id);


}
